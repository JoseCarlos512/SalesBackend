package sys_facturation.com.sunat;

import org.springframework.stereotype.Component;
import sys_facturation.com.config.SunatProperties;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.util.NumberToWords;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

/**
 * Generates a UBL 2.1 XML string for boleta (03) or factura (01) de venta,
 * compatible with SUNAT Peru (CustomizationID 2.0).
 *
 * The ExtensionContent node is left empty — BillingSignerService will insert
 * the XML digital signature there.
 */
@Component
public class BillingXmlBuilder {

    private static final BigDecimal IGV_RATE = new BigDecimal("0.18");
    private static final String CURRENCY = "PEN";

    private final SunatProperties props;

    public BillingXmlBuilder(SunatProperties props) {
        this.props = props;
    }

    public String build(Sales sale) {
        // ── Totals ────────────────────────────────────────────────────────────
        BigDecimal totalNeto = BigDecimal.ZERO;
        for (SalesDetails d : sale.getDetalles()) {
            BigDecimal lineNet = d.getPrecio()
                    .multiply(new BigDecimal(d.getCantidad()))
                    .subtract(d.getDescuento() != null ? d.getDescuento() : BigDecimal.ZERO);
            totalNeto = totalNeto.add(lineNet);
        }
        totalNeto = totalNeto.setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalIgv = totalNeto.multiply(IGV_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalPagar = totalNeto.add(totalIgv).setScale(2, RoundingMode.HALF_UP);

        String issueDate = sale.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String issueTime = sale.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String docId = sale.getSerieComprobante() + "-" + sale.getNumComprobante();
        String amountInWords = NumberToWords.convert(totalPagar, "SOLES");

        // Tipo: 01=Factura, 03=Boleta
        String invoiceTypeCode = sale.getTipoComprobante();

        // Customer document type: 6=RUC (factura), 1=DNI (boleta)
        String customerSchemeId = "01".equals(invoiceTypeCode) ? "6" : "1";
        String customerId = sale.getPerson() != null && sale.getPerson().getNum_documento() != null
                ? sale.getPerson().getNum_documento() : "00000000";
        String customerName = sale.getPerson() != null && sale.getPerson().getNombre() != null
                ? sale.getPerson().getNombre() : "CLIENTE VARIOS";

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\"\n");
        xml.append("  xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\"\n");
        xml.append("  xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\"\n");
        xml.append("  xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\"\n");
        xml.append("  xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n");

        // ── Extension placeholder for digital signature ──────────────────────
        xml.append("  <ext:UBLExtensions>\n");
        xml.append("    <ext:UBLExtension>\n");
        xml.append("      <ext:ExtensionContent/>\n");
        xml.append("    </ext:UBLExtension>\n");
        xml.append("  </ext:UBLExtensions>\n");

        // ── Document header ──────────────────────────────────────────────────
        xml.append("  <cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n");
        xml.append("  <cbc:CustomizationID>2.0</cbc:CustomizationID>\n");
        xml.append("  <cbc:ID>").append(escape(docId)).append("</cbc:ID>\n");
        xml.append("  <cbc:IssueDate>").append(issueDate).append("</cbc:IssueDate>\n");
        xml.append("  <cbc:IssueTime>").append(issueTime).append("</cbc:IssueTime>\n");
        xml.append("  <cbc:InvoiceTypeCode listID=\"0101\">").append(invoiceTypeCode).append("</cbc:InvoiceTypeCode>\n");
        xml.append("  <cbc:Note languageLocaleID=\"1000\"><![CDATA[").append(amountInWords).append("]]></cbc:Note>\n");
        xml.append("  <cbc:DocumentCurrencyCode>").append(CURRENCY).append("</cbc:DocumentCurrencyCode>\n");

        // ── Signature reference ──────────────────────────────────────────────
        xml.append("  <cac:Signature>\n");
        xml.append("    <cbc:ID>IDSignKG</cbc:ID>\n");
        xml.append("    <cac:SignatoryParty>\n");
        xml.append("      <cac:PartyIdentification><cbc:ID>").append(props.getRuc()).append("</cbc:ID></cac:PartyIdentification>\n");
        xml.append("      <cac:PartyName><cbc:Name><![CDATA[").append(props.getRazonSocial()).append("]]></cbc:Name></cac:PartyName>\n");
        xml.append("    </cac:SignatoryParty>\n");
        xml.append("    <cac:DigitalSignatureAttachment>\n");
        xml.append("      <cac:ExternalReference><cbc:URI>#signatureKG</cbc:URI></cac:ExternalReference>\n");
        xml.append("    </cac:DigitalSignatureAttachment>\n");
        xml.append("  </cac:Signature>\n");

        // ── Supplier (emisor) ────────────────────────────────────────────────
        xml.append("  <cac:AccountingSupplierParty>\n");
        xml.append("    <cac:Party>\n");
        xml.append("      <cac:PartyIdentification><cbc:ID schemeID=\"6\">").append(props.getRuc()).append("</cbc:ID></cac:PartyIdentification>\n");
        xml.append("      <cac:PartyName><cbc:Name><![CDATA[").append(props.getNombreComercial()).append("]]></cbc:Name></cac:PartyName>\n");
        xml.append("      <cac:PartyLegalEntity>\n");
        xml.append("        <cbc:RegistrationName><![CDATA[").append(props.getRazonSocial()).append("]]></cbc:RegistrationName>\n");
        xml.append("        <cac:RegistrationAddress>\n");
        xml.append("          <cbc:AddressTypeCode>0000</cbc:AddressTypeCode>\n");
        xml.append("        </cac:RegistrationAddress>\n");
        xml.append("      </cac:PartyLegalEntity>\n");
        xml.append("    </cac:Party>\n");
        xml.append("  </cac:AccountingSupplierParty>\n");

        // ── Customer (cliente) ───────────────────────────────────────────────
        xml.append("  <cac:AccountingCustomerParty>\n");
        xml.append("    <cac:Party>\n");
        xml.append("      <cac:PartyIdentification><cbc:ID schemeID=\"").append(customerSchemeId).append("\">")
                .append(escape(customerId)).append("</cbc:ID></cac:PartyIdentification>\n");
        xml.append("      <cac:PartyLegalEntity>\n");
        xml.append("        <cbc:RegistrationName><![CDATA[").append(customerName).append("]]></cbc:RegistrationName>\n");
        xml.append("      </cac:PartyLegalEntity>\n");
        xml.append("    </cac:Party>\n");
        xml.append("  </cac:AccountingCustomerParty>\n");

        // ── Tax totals ───────────────────────────────────────────────────────
        xml.append("  <cac:TaxTotal>\n");
        xml.append("    <cbc:TaxAmount currencyID=\"").append(CURRENCY).append("\">").append(totalIgv).append("</cbc:TaxAmount>\n");
        xml.append("    <cac:TaxSubtotal>\n");
        xml.append("      <cbc:TaxableAmount currencyID=\"").append(CURRENCY).append("\">").append(totalNeto).append("</cbc:TaxableAmount>\n");
        xml.append("      <cbc:TaxAmount currencyID=\"").append(CURRENCY).append("\">").append(totalIgv).append("</cbc:TaxAmount>\n");
        xml.append("      <cac:TaxCategory>\n");
        xml.append("        <cbc:ID>S</cbc:ID>\n");
        xml.append("        <cac:TaxScheme><cbc:ID>1000</cbc:ID><cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode></cac:TaxScheme>\n");
        xml.append("      </cac:TaxCategory>\n");
        xml.append("    </cac:TaxSubtotal>\n");
        xml.append("  </cac:TaxTotal>\n");

        // ── Monetary totals ──────────────────────────────────────────────────
        xml.append("  <cac:LegalMonetaryTotal>\n");
        xml.append("    <cbc:LineExtensionAmount currencyID=\"").append(CURRENCY).append("\">").append(totalNeto).append("</cbc:LineExtensionAmount>\n");
        xml.append("    <cbc:TaxInclusiveAmount currencyID=\"").append(CURRENCY).append("\">").append(totalPagar).append("</cbc:TaxInclusiveAmount>\n");
        xml.append("    <cbc:PayableAmount currencyID=\"").append(CURRENCY).append("\">").append(totalPagar).append("</cbc:PayableAmount>\n");
        xml.append("  </cac:LegalMonetaryTotal>\n");

        // ── Invoice lines ────────────────────────────────────────────────────
        int lineNum = 1;
        for (SalesDetails d : sale.getDetalles()) {
            BigDecimal unitNetPrice = d.getPrecio().setScale(2, RoundingMode.HALF_UP);
            BigDecimal discount = d.getDescuento() != null ? d.getDescuento() : BigDecimal.ZERO;
            BigDecimal lineNet = unitNetPrice.multiply(new BigDecimal(d.getCantidad())).subtract(discount).setScale(2, RoundingMode.HALF_UP);
            BigDecimal lineIgv = lineNet.multiply(IGV_RATE).setScale(2, RoundingMode.HALF_UP);
            BigDecimal unitPriceWithIgv = unitNetPrice.multiply(BigDecimal.ONE.add(IGV_RATE)).setScale(2, RoundingMode.HALF_UP);

            String itemCode = d.getArticle() != null && d.getArticle().getCodigo() != null
                    ? d.getArticle().getCodigo() : "PROD" + String.format("%03d", d.getId());
            String itemName = d.getArticle() != null && d.getArticle().getNombre() != null
                    ? d.getArticle().getNombre() : "Producto";

            xml.append("  <cac:InvoiceLine>\n");
            xml.append("    <cbc:ID>").append(lineNum++).append("</cbc:ID>\n");
            xml.append("    <cbc:InvoicedQuantity unitCode=\"NIU\">").append(d.getCantidad()).append("</cbc:InvoicedQuantity>\n");
            xml.append("    <cbc:LineExtensionAmount currencyID=\"").append(CURRENCY).append("\">").append(lineNet).append("</cbc:LineExtensionAmount>\n");
            xml.append("    <cac:PricingReference>\n");
            xml.append("      <cac:AlternativeConditionPrice>\n");
            xml.append("        <cbc:PriceAmount currencyID=\"").append(CURRENCY).append("\">").append(unitPriceWithIgv).append("</cbc:PriceAmount>\n");
            xml.append("        <cbc:PriceTypeCode>01</cbc:PriceTypeCode>\n");
            xml.append("      </cac:AlternativeConditionPrice>\n");
            xml.append("    </cac:PricingReference>\n");
            xml.append("    <cac:TaxTotal>\n");
            xml.append("      <cbc:TaxAmount currencyID=\"").append(CURRENCY).append("\">").append(lineIgv).append("</cbc:TaxAmount>\n");
            xml.append("      <cac:TaxSubtotal>\n");
            xml.append("        <cbc:TaxableAmount currencyID=\"").append(CURRENCY).append("\">").append(lineNet).append("</cbc:TaxableAmount>\n");
            xml.append("        <cbc:TaxAmount currencyID=\"").append(CURRENCY).append("\">").append(lineIgv).append("</cbc:TaxAmount>\n");
            xml.append("        <cac:TaxCategory>\n");
            xml.append("          <cbc:ID>S</cbc:ID>\n");
            xml.append("          <cac:TaxScheme><cbc:ID>1000</cbc:ID><cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode></cac:TaxScheme>\n");
            xml.append("        </cac:TaxCategory>\n");
            xml.append("      </cac:TaxSubtotal>\n");
            xml.append("    </cac:TaxTotal>\n");
            xml.append("    <cac:Item>\n");
            xml.append("      <cbc:Description><![CDATA[").append(itemName).append("]]></cbc:Description>\n");
            xml.append("      <cac:SellersItemIdentification><cbc:ID>").append(escape(itemCode)).append("</cbc:ID></cac:SellersItemIdentification>\n");
            xml.append("    </cac:Item>\n");
            xml.append("    <cac:Price>\n");
            xml.append("      <cbc:PriceAmount currencyID=\"").append(CURRENCY).append("\">").append(unitNetPrice).append("</cbc:PriceAmount>\n");
            xml.append("    </cac:Price>\n");
            xml.append("  </cac:InvoiceLine>\n");
        }

        xml.append("</Invoice>");
        return xml.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
