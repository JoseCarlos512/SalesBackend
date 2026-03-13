package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.dto.UserDTO;
import sys_facturation.com.entity.User;
import sys_facturation.com.repository.UserDao;
import sys_facturation.com.service.UserService;
import sys_facturation.com.util.MapperUtils;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserImplement implements UserService {

    @Autowired
    private UserDao userRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(MapperUtils::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(MapperUtils::toUserDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserDTO insert(User user) {
        return MapperUtils.toUserDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO update(User user) {
        return MapperUtils.toUserDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
