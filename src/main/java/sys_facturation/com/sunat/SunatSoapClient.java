package sys_facturation.com.sunat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sys_facturation.com.config.SunatProperties;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Sends signed billing XML to SUNAT via SOAP 1.1.
 *
 * SUNAT bill service operations:
 *   - sendBill   → for individual vouchers (boleta/factura)
 *   - sendSummary → for daily summary of boletas
 */
@Component
public class SunatSoapClient {

    private static final Logger log = LoggerFactory.getLogger(SunatSoapClient.class);

    private final SunatProperties props;
    private final RestTemplate restTemplate;

    public SunatSoapClient(SunatProperties props) {
        this.props = props;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Calls SUNAT's sendBill operation.
     *
     * @param fileName   e.g. "20000000001-03-B001-00000001.zip"
     * @param zipContent ZIP file bytes containing the signed XML
     * @return CDR (Constancia de Recepción) as base64 string, or null on error
     */
    public String sendBill(String fileName, byte[] zipContent) throws Exception {
        String contentBase64 = Base64.getEncoder().encodeToString(zipContent);

        String soapBody = buildSoapEnvelope(fileName, contentBase64);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.setAccept(java.util.List.of(MediaType.TEXT_XML));
        headers.set("SOAPAction", "\"\"");
        headers.set("Authorization", basicAuthHeader());

        HttpEntity<String> request = new HttpEntity<>(soapBody, headers);

        log.info("Enviando {} a SUNAT [{}]", fileName, props.getAmbiente());

        ResponseEntity<String> response = restTemplate.exchange(
                props.getBillServiceUrl(),
                HttpMethod.POST,
                request,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("SUNAT respondió con HTTP " + response.getStatusCode());
        }

        return extractCdrFromResponse(response.getBody());
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private String buildSoapEnvelope(String fileName, String contentBase64) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                " xmlns:ser=\"http://service.sunat.gob.pe\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<ser:sendBill>" +
                "<fileName>" + fileName + "</fileName>" +
                "<contentFile>" + contentBase64 + "</contentFile>" +
                "</ser:sendBill>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String basicAuthHeader() {
        String credentials = props.getUsername() + ":" + props.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private String extractCdrFromResponse(String soapResponse) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder()
                .parse(new ByteArrayInputStream(soapResponse.getBytes(StandardCharsets.UTF_8)));

        NodeList nodes = doc.getElementsByTagName("applicationResponse");
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent().trim();
        }

        // SUNAT fault
        NodeList faultString = doc.getElementsByTagName("faultstring");
        if (faultString.getLength() > 0) {
            throw new RuntimeException("SUNAT fault: " + faultString.item(0).getTextContent());
        }

        throw new RuntimeException("Respuesta inesperada de SUNAT: " + soapResponse);
    }
}
