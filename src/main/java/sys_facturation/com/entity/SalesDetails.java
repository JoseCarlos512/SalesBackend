package sys_facturation.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class SalesDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    /** Unit price WITHOUT IGV (net price) */
    @Column(name = "precio", nullable = false, precision = 11, scale = 2)
    private BigDecimal precio;

    @Column(name = "descuento", nullable = false, precision = 11, scale = 2)
    private BigDecimal descuento;

    /** The product sold — required for SUNAT XML line item description */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "categories"})
    private Articles article;

    /** Owning side of the Sales relationship. Hidden from JSON to avoid circular reference. */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale", nullable = false)
    private Sales sales;

    public SalesDetails() {
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }

    public Articles getArticle() { return article; }
    public void setArticle(Articles article) { this.article = article; }

    public Sales getSales() { return sales; }
    public void setSales(Sales sales) { this.sales = sales; }
}
