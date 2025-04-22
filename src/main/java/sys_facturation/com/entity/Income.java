package sys_facturation.com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingresos")
public class Income implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idproveedor", nullable = false)
    private Long idproveedor;

    @Column(name = "idusuario", nullable = false)
    private Long idusuario;
    
    @Column(name = "tipo_comprobante", nullable = false)
    private String tipo_comprobante;

    @Column(name = "serie_comprobante")
    private String serie_comprobante;

    @Column(name = "num_comprobante")
    private String num_comprobante;

    @Column(name = "impuesto")
    private BigDecimal impuesto;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "estado")
    private String estado = "ACTIVO";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fecha_hora;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "income", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncomeDetail> detalles = new ArrayList<>();

    //  @PrePersist
    // protected void onCreate() {
    //     if (this.fecha_hora == null) {
    //         this.fecha_hora = LocalDateTime.now();
    //     }
    //     if (this.created_at == null) {
    //         this.created_at = LocalDateTime.now();
    //     }
    //     this.updated_at = LocalDateTime.now();
    // }

    public Income() {
    }

    public Income(Long id, Long idproveedor, Long idusuario, String tipo_comprobante, String serie_comprobante,
            String num_comprobante, LocalDateTime fecha_hora, BigDecimal impuesto, BigDecimal total, String estado,
            LocalDateTime created_at, LocalDateTime updated_at, List<IncomeDetail> detalles) {
        this.id = id;
        this.idproveedor = idproveedor;
        this.idusuario = idusuario;
        this.tipo_comprobante = tipo_comprobante;
        this.serie_comprobante = serie_comprobante;
        this.num_comprobante = num_comprobante;
        this.fecha_hora = fecha_hora;
        this.impuesto = impuesto;
        this.total = total;
        this.estado = estado;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(String tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public String getSerie_comprobante() {
        return serie_comprobante;
    }

    public void setSerie_comprobante(String serie_comprobante) {
        this.serie_comprobante = serie_comprobante;
    }

    public String getNum_comprobante() {
        return num_comprobante;
    }

    public void setNum_comprobante(String num_comprobante) {
        this.num_comprobante = num_comprobante;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public List<IncomeDetail> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<IncomeDetail> detalles) {
        this.detalles = detalles;
    }

}
