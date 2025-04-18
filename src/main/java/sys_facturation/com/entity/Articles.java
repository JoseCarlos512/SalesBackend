package sys_facturation.com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Articles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 256)
    private String descripcion;

    @Column(length = 50)
    private String codigo;

    @Column(length = 50, nullable = false)
    private Double precio_venta;

    @Column(length = 11)
    private int stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime create_at = LocalDateTime.now(); // Fecha específica para este registro.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime update_at = LocalDateTime.now(); // Fecha específica para este registro.

    @Column(nullable = false, length = 1)
    private int status_article = 1;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_categories")
    Categories categories;

    public Articles() {
    }

    public Articles(Long id, String nombre, String descripcion, String codigo, Double precio_venta, int stock, LocalDateTime create_at, LocalDateTime update_at, int status_article, Categories categories) {
        this.Id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio_venta = precio_venta;
        this.stock = stock;
        this.create_at = create_at;
        this.update_at = update_at;
        this.status_article = status_article;
        this.categories = categories;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public int getStatus_article() {
        return status_article;
    }

    public void setStatus_article(int status_article) {
        this.status_article = status_article;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
