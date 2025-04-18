package sys_facturation.com.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String usuario;
    @Column(nullable = false, length = 255)
    private String password;
    private Boolean condicion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrol", referencedColumnName = "id")
    private Rol rol;
    private String rememberToken;

    public User() {
    }

    public User(Long id, String usuario, String password, Boolean condicion, Rol rol, String rememberToken) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.condicion = condicion;
        this.rol = rol;
        this.rememberToken = rememberToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCondicion() {
        return condicion;
    }

    public void setCondicion(Boolean condicion) {
        this.condicion = condicion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    private static final long serialVersionUID = 1L;
}
