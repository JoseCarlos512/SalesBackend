package sys_facturation.com.service;

import sys_facturation.com.dto.UserDTO;
import sys_facturation.com.entity.User;

import java.util.Collection;

public interface UserService {
    Collection<UserDTO> findAll();
    UserDTO findById(Long Id);
    UserDTO insert(User user);
    UserDTO update(User user);
    boolean deleteById(Long Id);
    boolean existsById(Long Id);
}
