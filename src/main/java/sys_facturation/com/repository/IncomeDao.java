package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;

import sys_facturation.com.entity.Income;

public interface IncomeDao extends CrudRepository<Income, Long> {
}