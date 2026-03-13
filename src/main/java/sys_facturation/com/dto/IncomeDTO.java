package sys_facturation.com.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import sys_facturation.com.entity.Income;
import sys_facturation.com.entity.IncomeDetail;

public class IncomeDTO {
    //private Long id;
    public Long idproveedor;
    public Long idusuario;
    public String tipoComprobante;
    public String serieComprobante;
    public String numComprobante;
    public BigDecimal impuesto;
    public BigDecimal total;
    public String estado;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public LocalDateTime fechaHora;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public LocalDateTime updatedAt;

    // @Valid
    // @NotEmpty(message = "Debe incluir al menos un detalle")
     public List<IncomeDetailDTO> detalles;

    public IncomeDTO() {
    }

    public Income toEntity() {
        Income income = new Income();
        income.setIdproveedor(this.idproveedor);
        income.setIdusuario(this.idusuario);
        income.setTipoComprobante(this.tipoComprobante);
        income.setSerieComprobante(this.serieComprobante);
        income.setNumComprobante(this.numComprobante);
        income.setImpuesto(this.impuesto);
        income.setTotal(this.total);
        income.setEstado(this.estado);
        income.setFechaHora(this.fechaHora);

        if (this.detalles != null) {
            List<IncomeDetail> detallesEntity = this.detalles.stream()
                .map(d -> d.toEntity(income))
                .toList();
            income.setDetalles(detallesEntity);
        }

        return income;
    }

    public Long getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Long idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipo_comprobante) {
        this.tipoComprobante = tipo_comprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serie_comprobante) {
        this.serieComprobante = serie_comprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String num_comprobante) {
        this.numComprobante = num_comprobante;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}
