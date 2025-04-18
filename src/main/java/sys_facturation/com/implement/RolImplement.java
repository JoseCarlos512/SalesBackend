package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.dto.RolDTO;
import sys_facturation.com.entity.Rol;
import sys_facturation.com.repository.RolDao;
import sys_facturation.com.service.RolService;
import sys_facturation.com.util.MapperUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolImplement implements RolService {

    @Autowired
    RolDao rolRepository;

    @Override
    public Collection<RolDTO> findAll() {
        return ((Collection<Rol>) rolRepository.findAll()).stream()
                .map(MapperUtils::toRolDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO findById(Long Id) {
        Rol rol = rolRepository.findById(Id).orElse(null);
        return rol != null ? MapperUtils.toRolDTO(rol) : null;
    }

    @Override
    @Transactional
    public RolDTO insert(Rol rol) {
        Rol saved = rolRepository.save(rol);
        return MapperUtils.toRolDTO(saved);
    }

    @Override
    @Transactional
    public RolDTO update(Rol rol) {
        Rol updated = rolRepository.save(rol);
        return MapperUtils.toRolDTO(updated);
    }

    @Override
    @Transactional
    public boolean deleteById(Long Id) {
        if (rolRepository.existsById(Id)){
            rolRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(Long Id) {
        return rolRepository.existsById(Id);
    }
}
