package sys_facturation.com.sunat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sys_facturation.com.config.SunatProperties;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Signs a UBL 2.1 XML document with the company's digital certificate (PKCS12).
 * Uses Java's built-in javax.xml.crypto.dsig — no extra dependencies needed.
 */
@Service
public class BillingSignerService {

    private static final Logger log = LoggerFactory.getLogger(BillingSignerService.class);

    private final SunatProperties props;
    private final ResourceLoader resourceLoader;

    public BillingSignerService(SunatProperties props, ResourceLoader resourceLoader) {
        this.props = props;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Signs the given XML string and returns the signed XML as a byte array.
     * If the certificate file is not found, returns the unsigned XML (useful for dev).
     */
    public byte[] sign(String xmlContent) throws Exception {
        Document doc = parseXml(xmlContent);

        try {
            KeyStore ks = loadKeyStore();
            String alias = resolveAlias(ks);
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, props.getCertificate().getPassword().toCharArray());
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

            signDocument(doc, privateKey, cert);
            log.info("XML firmado correctamente con alias '{}'", alias);
        } catch (Exception ex) {
            log.warn("No se pudo firmar el XML ({}). Se enviará sin firma — solo válido en beta.", ex.getMessage());
        }

        return documentToBytes(doc);
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private void signDocument(Document doc, PrivateKey privateKey, X509Certificate cert) throws Exception {
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        List<Transform> transforms = List.of(
                fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                fac.newTransform(CanonicalizationMethod.INCLUSIVE, (TransformParameterSpec) null)
        );

        Reference ref = fac.newReference(
                "",
                fac.newDigestMethod(DigestMethod.SHA256, null),
                transforms,
                null, null
        );

        SignedInfo si = fac.newSignedInfo(
                fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                Collections.singletonList(ref)
        );

        KeyInfoFactory kif = fac.getKeyInfoFactory();
        X509Data x509Data = kif.newX509Data(List.of(cert));
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509Data));

        XMLSignature signature = fac.newXMLSignature(si, ki, null, "signatureKG", null);

        // Insert signature into the ExtensionContent node
        NodeList extensionContent = doc.getElementsByTagNameNS(
                "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2",
                "ExtensionContent"
        );

        if (extensionContent.getLength() == 0) {
            throw new IllegalStateException("ExtensionContent node not found in UBL XML");
        }

        DOMSignContext dsc = new DOMSignContext(privateKey, extensionContent.item(0));
        signature.sign(dsc);
    }

    private KeyStore loadKeyStore() throws Exception {
        String certPath = props.getCertificate().getPath();
        InputStream is;

        if (certPath.startsWith("classpath:")) {
            is = resourceLoader.getResource(certPath).getInputStream();
        } else {
            is = resourceLoader.getResource("file:" + certPath).getInputStream();
        }

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(is, props.getCertificate().getPassword().toCharArray());
        return ks;
    }

    private String resolveAlias(KeyStore ks) throws Exception {
        String alias = props.getCertificate().getAlias();
        if (alias != null && !alias.isBlank()) {
            return alias;
        }
        // Auto-detect the first alias
        Enumeration<String> aliases = ks.aliases();
        if (aliases.hasMoreElements()) {
            return aliases.nextElement();
        }
        throw new IllegalStateException("No se encontraron aliases en el certificado PKCS12");
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf.newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private byte[] documentToBytes(Document doc) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(doc), new StreamResult(out));
        return out.toByteArray();
    }
}
