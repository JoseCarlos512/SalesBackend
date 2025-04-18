package sys_facturation.com.util;

import sys_facturation.com.dto.RolDTO;
import sys_facturation.com.dto.UserDTO;
import sys_facturation.com.entity.Rol;
import sys_facturation.com.entity.User;

public class MapperUtils {

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsuario(user.getUsuario());
        dto.setPassword(user.getPassword());
        dto.setIdRol(user.getRol().getId().intValue());
        return dto;
    }

    public static RolDTO toRolDTO(Rol rol) {
        if (rol == null) return null;

        RolDTO dto = new RolDTO();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        return dto;
    }
}
