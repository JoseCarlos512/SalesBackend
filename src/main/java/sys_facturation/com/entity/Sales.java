package sys_facturation.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_comprobante", nullable = false, length = 20)
    private String tipoComprobante;

    @Column(name = "serie_comprobante", length = 7)
    private String serieComprobante;

    @Column(name = "num_comprobante", nullable = false, length = 10)
    private String numComprobante;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /** IGV rate (e.g. 0.18 for 18%) */
    @Column(name = "impuesto", nullable = false, precision = 4, scale = 2)
    private BigDecimal impuesto;

    @Column(name = "total", nullable = false, precision = 11, scale = 2)
    private BigDecimal total;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    /** SUNAT response code after sending (e.g. 0 = accepted) */
    @Column(name = "sunat_codigo", length = 10)
    private String sunatCodigo;

    /** SUNAT response description */
    @Column(name = "sunat_descripcion", length = 250)
    private String sunatDescripcion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** Customer — required for SUNAT boleta/factura */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Person person;

    /** Line items — cascade so saving a Sale also saves its details */
    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SalesDetails> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Sales() {
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }

    public String getSerieComprobante() { return serieComprobante; }
    public void setSerieComprobante(String serieComprobante) { this.serieComprobante = serieComprobante; }

    public String getNumComprobante() { return numComprobante; }
    public void setNumComprobante(String numComprobante) { this.numComprobante = numComprobante; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public BigDecimal getImpuesto() { return impuesto; }
    public void setImpuesto(BigDecimal impuesto) { this.impuesto = impuesto; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getSunatCodigo() { return sunatCodigo; }
    public void setSunatCodigo(String sunatCodigo) { this.sunatCodigo = sunatCodigo; }

    public String getSunatDescripcion() { return sunatDescripcion; }
    public void setSunatDescripcion(String sunatDescripcion) { this.sunatDescripcion = sunatDescripcion; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public List<SalesDetails> getDetalles() { return detalles; }
    public void setDetalles(List<SalesDetails> detalles) { this.detalles = detalles; }
}
