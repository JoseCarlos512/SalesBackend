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
import java.util.stream.Collectors;

@Service
public class RolImplement implements RolService {

    @Autowired
    private RolDao rolRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<RolDTO> findAll() {
        return rolRepository.findAll().stream()
                .map(MapperUtils::toRolDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO findById(Long id) {
        return rolRepository.findById(id)
                .map(MapperUtils::toRolDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public RolDTO insert(Rol rol) {
        return MapperUtils.toRolDTO(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public RolDTO update(Rol rol) {
        return MapperUtils.toRolDTO(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return rolRepository.existsById(id);
    }
}
