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

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column( length = 256)
    private String descripcion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime create_at = LocalDateTime.now(); // Fecha específica para este registro.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime update_at = LocalDateTime.now(); // Fecha específica para este registro.

    @Column(nullable = false, length = 1)
    private int condicion = 1;

    public Categories() {
    }

    public Categories(Long id, String nombre, String descripcion, LocalDateTime create_at, LocalDateTime update_at, int condicion) {
        this.Id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.create_at = create_at;
        this.update_at = update_at;
        this.condicion = condicion;
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

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }
}
