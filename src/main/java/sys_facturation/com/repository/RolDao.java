package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Rol;

public interface RolDao extends JpaRepository<Rol, Long> {
}
