package sys_facturation.com.dto;

public class UserDTO {
    private Long id;
    private String usuario;
    private String password;
    private int idRol;

    public UserDTO() {
    }

    public UserDTO(Long id, String usuario, String password, int idRol) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.idRol = idRol;
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

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
