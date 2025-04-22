package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;

import sys_facturation.com.entity.Person;

public interface PersonDao extends CrudRepository<Person, Long> {
}
