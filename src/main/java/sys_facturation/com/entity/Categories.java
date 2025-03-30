package sys_facturation.com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Categories implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 15)
    private String name_categorie;

    @Column( length = 45)
    private String description_categorie;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime create_at = LocalDateTime.now(); // Fecha específica para este registro.

    @Column(nullable = false, length = 1)
    private int status_categorie = 1;

    public Categories() {
    }

    public Categories(Long id, String name_categorie, String description_categorie, LocalDateTime create_at, int status_categorie) {
        this.Id = id;
        this.name_categorie = name_categorie;
        this.description_categorie = description_categorie;
        this.create_at = create_at;
        this.status_categorie = status_categorie;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public int getStatus_categorie() {
        return status_categorie;
    }

    public void setStatus_categorie(int status_categorie) {
        this.status_categorie = status_categorie;
    }
}
