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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImplement implements UserService {
    @Autowired
    UserDao userRepository;
    @Override
    public Collection<UserDTO> findAll() {
        return ((Collection<User>) userRepository.findAll()).stream()
                .map(MapperUtils::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long Id) {
        User user = userRepository.findById(Id).orElse(null);
        return user != null ? MapperUtils.toUserDTO(user) : null;
    }

    @Override
    @Transactional
    public UserDTO insert(User user) {
        User saved = userRepository.save(user);
        return MapperUtils.toUserDTO(saved);
    }

    @Override
    @Transactional
    public UserDTO update(User user) {
        User updated = userRepository.save(user);
        return MapperUtils.toUserDTO(updated);
    }

    @Override
    @Transactional
    public boolean deleteById(Long Id) {
        if (userRepository.existsById(Id)){
            userRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(Long Id) {
        return userRepository.existsById(Id);
    }
}
