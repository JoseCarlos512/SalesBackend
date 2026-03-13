package sys_facturation.com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sunat")
public class SunatProperties {

    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private String ubigeo;
    private String distrito;
    private String provincia;
    private String departamento;
    private String username;
    private String password;
    /** "beta" or "produccion" */
    private String ambiente = "beta";
    private Certificate certificate = new Certificate();

    // ── Nested ──────────────────────────────────────────────────────────────

    public static class Certificate {
        private String path = "classpath:cert/demo.pfx";
        private String password = "demo";
        private String alias = "";

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getAlias() { return alias; }
        public void setAlias(String alias) { this.alias = alias; }
    }

    /** WSDL endpoint resolved from ambiente */
    public String getBillServiceUrl() {
        return "produccion".equalsIgnoreCase(ambiente)
                ? "https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService"
                : "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getUbigeo() { return ubigeo; }
    public void setUbigeo(String ubigeo) { this.ubigeo = ubigeo; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAmbiente() { return ambiente; }
    public void setAmbiente(String ambiente) { this.ambiente = ambiente; }

    public Certificate getCertificate() { return certificate; }
    public void setCertificate(Certificate certificate) { this.certificate = certificate; }
}
