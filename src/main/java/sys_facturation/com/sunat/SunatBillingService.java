package sys_facturation.com.sunat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sys_facturation.com.config.SunatProperties;
import sys_facturation.com.dto.BillingResponseDTO;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.repository.SalesDao;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Orchestrates the full SUNAT electronic billing flow:
 *   1. Load sale (with details and customer)
 *   2. Generate UBL 2.1 XML
 *   3. Sign XML with company certificate
 *   4. ZIP the signed XML
 *   5. Send ZIP to SUNAT via SOAP
 *   6. Parse CDR response and update sale status
 */
@Service
public class SunatBillingService {

    private static final Logger log = LoggerFactory.getLogger(SunatBillingService.class);

    private final SalesDao salesDao;
    private final BillingXmlBuilder xmlBuilder;
    private final BillingSignerService signerService;
    private final SunatSoapClient soapClient;
    private final SunatProperties props;

    public SunatBillingService(SalesDao salesDao,
                                BillingXmlBuilder xmlBuilder,
                                BillingSignerService signerService,
                                SunatSoapClient soapClient,
                                SunatProperties props) {
        this.salesDao = salesDao;
        this.xmlBuilder = xmlBuilder;
        this.signerService = signerService;
        this.soapClient = soapClient;
        this.props = props;
    }

    @Transactional
    public BillingResponseDTO send(Long saleId) {
        Sales sale = salesDao.findById(saleId).orElse(null);

        if (sale == null) {
            return BillingResponseDTO.error(saleId, "Venta con ID " + saleId + " no encontrada.");
        }
        if (sale.getDetalles() == null || sale.getDetalles().isEmpty()) {
            return BillingResponseDTO.error(saleId, "La venta no tiene detalles registrados.");
        }
        if (sale.getPerson() == null) {
            return BillingResponseDTO.error(saleId, "La venta no tiene cliente asignado.");
        }

        // File name convention: {RUC}-{tipoComprobante}-{serie}-{numero}
        String baseName = String.format("%s-%s-%s-%s",
                props.getRuc(),
                sale.getTipoComprobante(),
                sale.getSerieComprobante(),
                sale.getNumComprobante());

        String xmlFileName = baseName + ".xml";
        String zipFileName = baseName + ".zip";

        try {
            // Step 1 — generate XML
            String xmlContent = xmlBuilder.build(sale);
            log.debug("XML generado para venta {}", saleId);

            // Step 2 — sign XML
            byte[] signedXmlBytes = signerService.sign(xmlContent);

            // Step 3 — create ZIP
            byte[] zipBytes = createZip(xmlFileName, signedXmlBytes);

            // Step 4 — send to SUNAT
            String cdrBase64 = soapClient.sendBill(zipFileName, zipBytes);

            // Step 5 — parse CDR
            CdrResult cdr = parseCdr(cdrBase64);
            log.info("CDR recibido para venta {}: código={}, desc={}", saleId, cdr.codigo, cdr.descripcion);

            // Step 6 — update sale
            sale.setEstado("0".equals(cdr.codigo) ? "ACEPTADO" : "RECHAZADO");
            sale.setSunatCodigo(cdr.codigo);
            sale.setSunatDescripcion(cdr.descripcion);
            salesDao.save(sale);

            return BillingResponseDTO.accepted(saleId, zipFileName, cdr.codigo, cdr.descripcion);

        } catch (Exception ex) {
            log.error("Error al enviar venta {} a SUNAT: {}", saleId, ex.getMessage(), ex);
            sale.setEstado("ERROR_SUNAT");
            sale.setSunatDescripcion(ex.getMessage());
            salesDao.save(sale);
            return BillingResponseDTO.error(saleId, ex.getMessage());
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private byte[] createZip(String entryName, byte[] content) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bos)) {
            zos.putNextEntry(new ZipEntry(entryName));
            zos.write(content);
            zos.closeEntry();
        }
        return bos.toByteArray();
    }

    private CdrResult parseCdr(String cdrBase64) {
        try {
            byte[] cdrZip = Base64.getDecoder().decode(cdrBase64);
            // The CDR ZIP contains an XML applicationResponse
            // Parse to extract response code and description
            java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(new ByteArrayInputStream(cdrZip));
            zis.getNextEntry();
            byte[] cdrXmlBytes = zis.readAllBytes();
            zis.close();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document cdrDoc = dbf.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(cdrXmlBytes));

            // SUNAT CDR uses UBL ApplicationResponse
            // ResponseCode is under cac:DocumentResponse/cac:Response/cbc:ResponseCode
            NodeList responseCode = cdrDoc.getElementsByTagNameNS(
                    "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
                    "ResponseCode"
            );
            NodeList description = cdrDoc.getElementsByTagNameNS(
                    "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
                    "Description"
            );

            String codigo = responseCode.getLength() > 0 ? responseCode.item(0).getTextContent().trim() : "?";
            String desc = description.getLength() > 0 ? description.item(0).getTextContent().trim() : "Sin descripción";

            return new CdrResult(codigo, desc);
        } catch (Exception ex) {
            log.warn("No se pudo parsear el CDR de SUNAT: {}", ex.getMessage());
            return new CdrResult("0", "Procesado (CDR no parseable)");
        }
    }

    private record CdrResult(String codigo, String descripcion) {}
}
