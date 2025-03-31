package sys_facturation.com.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class Person implements Serializable{
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
    private Long id;
    private String nombre;
    private String tipo_documento;
    private String num_documento;    
    private String direccion;    
    private String telefono;    
    private String email;    
    @Column(name="created_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;    
    private LocalDateTime updated_at; 
    
    //@OneToMany(mappedBy = "personas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Ventas> ventas;

    //@OneToMany(mappedBy = "personas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Users> users;

    public Person() {
        //ventas = new ArrayList<>();
        //users = new ArrayList<>();
    }

    public Person(Long id, String nombre, String tipo_documento, String num_documento, String direccion,
                    String telefono, String email, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.nombre = nombre;
        this.tipo_documento = tipo_documento;
        this.num_documento = num_documento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNum_documento() {
        return num_documento;
    }

    public void setNum_documento(String num_documento) {
        this.num_documento = num_documento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreate_at() {
        return created_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.created_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return updated_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.updated_at = update_at;
    }
}
