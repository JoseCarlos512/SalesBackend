package sys_facturation.com.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class SalesDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false, precision = 11, scale = 2)
    private BigDecimal precio;

    @Column(name = "descuento", nullable = false, precision = 11, scale = 2)
    private BigDecimal descuento;

    @ManyToOne
    @JoinColumn(name = "id_sale", nullable = false)
    Sales sales;

    public SalesDetails() {
    }

    public SalesDetails(Long id, Integer cantidad, BigDecimal precio, BigDecimal descuento, Sales sales) {
        this.Id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.sales = sales;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }
}
