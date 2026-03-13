package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Income;

public interface IncomeDao extends JpaRepository<Income, Long> {
}
