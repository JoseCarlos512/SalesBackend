package sys_facturation.com.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String nombre;
    @Column(nullable = true, length = 100)
    private String descripcion;
    private int condicion;

    public Rol() {
    }

    public Rol(Long id, String nombre, String descripcion, int condicion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.condicion = condicion;
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

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }


    private static final long serialVersionUID = 1L;

}
