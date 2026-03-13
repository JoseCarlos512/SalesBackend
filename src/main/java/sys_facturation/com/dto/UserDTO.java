package sys_facturation.com.dto;

public class UserDTO {
    private Long id;
    private String usuario;
    private int idRol;

    public UserDTO() {
    }

    public UserDTO(Long id, String usuario, int idRol) {
        this.id = id;
        this.usuario = usuario;
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

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
