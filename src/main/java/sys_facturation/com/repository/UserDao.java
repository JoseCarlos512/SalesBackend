package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;
import sys_facturation.com.entity.User;

public interface UserDao extends CrudRepository<User, Long> {
}
