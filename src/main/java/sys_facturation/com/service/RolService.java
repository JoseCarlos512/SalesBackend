package sys_facturation.com.service;

import sys_facturation.com.dto.RolDTO;
import sys_facturation.com.entity.Rol;

import java.util.Collection;

public interface RolService {
    Collection<RolDTO> findAll();
    RolDTO findById(Long Id);
    RolDTO insert(Rol rol);
    RolDTO update(Rol rol);
    boolean deleteById(Long Id);
    boolean existsById(Long Id);
}
