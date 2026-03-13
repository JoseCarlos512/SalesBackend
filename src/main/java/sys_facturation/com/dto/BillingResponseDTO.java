package sys_facturation.com.dto;

public class BillingResponseDTO {

    private Long saleId;
    /** ACEPTADO | RECHAZADO | ERROR */
    private String estado;
    private String codigoSunat;
    private String descripcionSunat;
    /** Name of the ZIP file sent to SUNAT */
    private String fileName;

    public BillingResponseDTO() {
    }

    public static BillingResponseDTO accepted(Long saleId, String fileName, String codigo, String descripcion) {
        BillingResponseDTO r = new BillingResponseDTO();
        r.saleId = saleId;
        r.estado = "ACEPTADO";
        r.fileName = fileName;
        r.codigoSunat = codigo;
        r.descripcionSunat = descripcion;
        return r;
    }

    public static BillingResponseDTO error(Long saleId, String mensaje) {
        BillingResponseDTO r = new BillingResponseDTO();
        r.saleId = saleId;
        r.estado = "ERROR";
        r.descripcionSunat = mensaje;
        return r;
    }

    public Long getSaleId() { return saleId; }
    public void setSaleId(Long saleId) { this.saleId = saleId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoSunat() { return codigoSunat; }
    public void setCodigoSunat(String codigoSunat) { this.codigoSunat = codigoSunat; }

    public String getDescripcionSunat() { return descripcionSunat; }
    public void setDescripcionSunat(String descripcionSunat) { this.descripcionSunat = descripcionSunat; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
