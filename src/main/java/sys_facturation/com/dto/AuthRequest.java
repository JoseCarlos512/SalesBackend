package sys_facturation.com.dto;

public class AuthRequest {
    private String usuario;
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String usuario) {
        this.usuario = usuario;
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
}
