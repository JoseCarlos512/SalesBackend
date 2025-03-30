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

    @Column(nullable = false, length = 30)
    private String name_article;

    @Column(length = 50)
    private String description;

    @Column(length = 15)
    private String code_article;

    @Column(nullable = false, length = 10)
    private Double price;

    @Column(length = 10)
    private int stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime create_at = LocalDateTime.now(); // Fecha específica para este registro.

    @Column(nullable = false, length = 1)
    private int status_article = 1;

    @ManyToOne
    @JoinColumn(nullable = false, name = "categories_id")
    Categories categories;

    public Articles() {
    }

    public Articles(Long id, String name_article, String description, String code_article, Double price, int stock, LocalDateTime create_at, int status_article, Categories categories) {
        this.Id = id;
        this.name_article = name_article;
        this.description = description;
        this.code_article = code_article;
        this.price = price;
        this.stock = stock;
        this.create_at = create_at;
        this.status_article = status_article;
        this.categories = categories;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName_article() {
        return name_article;
    }

    public void setName_article(String name_article) {
        this.name_article = name_article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode_article() {
        return code_article;
    }

    public void setCode_article(String code_article) {
        this.code_article = code_article;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
