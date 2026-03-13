package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Person;

public interface PersonDao extends JpaRepository<Person, Long> {
}
