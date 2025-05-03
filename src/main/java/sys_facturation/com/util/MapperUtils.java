package sys_facturation.com.util;

import sys_facturation.com.dto.IncomeDTO;
import sys_facturation.com.dto.RolDTO;
import sys_facturation.com.dto.UserDTO;
import sys_facturation.com.entity.Income;
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

    public static IncomeDTO toIncomeDTO(Income income) {
        if (income == null) return null;

        IncomeDTO dto = new IncomeDTO();
        dto.setIdproveedor(income.getIdproveedor());
        dto.setIdusuario(income.getIdusuario());
        dto.setTipoComprobante(income.getTipoComprobante());
        dto.setNumComprobante(income.getNumComprobante());
        dto.setFechaHora(income.getFechaHora());
        dto.setImpuesto(income.getImpuesto());
        dto.setTotal(income.getTotal());
        dto.setEstado(income.getEstado());
        dto.setCreatedAt(income.getCreatedAt());
        dto.setUpdatedAt(income.getUpdatedAt());
        return dto;
    }
}
