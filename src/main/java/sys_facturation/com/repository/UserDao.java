package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;
import sys_facturation.com.entity.User;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByUsuario(String usuario);
}
