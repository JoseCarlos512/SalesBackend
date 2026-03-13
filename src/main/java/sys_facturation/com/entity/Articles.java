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
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 256)
    private String descripcion;

    @Column(length = 50)
    private String codigo;

    @Column(nullable = false)
    private Double precio_venta;

    @Column(length = 11)
    private int stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(nullable = false, updatable = false)
    private LocalDateTime create_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(nullable = false)
    private LocalDateTime update_at;

    @Column(nullable = false, length = 1)
    private int status_article = 1;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_categories")
    Categories categories;

    @PrePersist
    protected void onCreate() {
        create_at = LocalDateTime.now();
        update_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        update_at = LocalDateTime.now();
    }

    public Articles() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUpdate_at() {
        return update_at;
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
