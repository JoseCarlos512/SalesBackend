package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsuario(String usuario);
}
