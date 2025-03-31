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
@Table(name = "proveedores")
public class Provider implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contacto;
    private String telefono_contacto;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;
    
    private LocalDateTime updated_at;

    public Provider() {
        //ventas = new ArrayList<>();
        //users = new ArrayList<>();
    }

    public Provider(Long id, String contacto, String telefono_contacto, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.contacto = contacto;
        this.telefono_contacto = telefono_contacto;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public LocalDateTime getCreate_at() {
        return created_at;
    }

    public void setCreate_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdate_at() {
        return updated_at;
    }

    public void setUpdate_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}

