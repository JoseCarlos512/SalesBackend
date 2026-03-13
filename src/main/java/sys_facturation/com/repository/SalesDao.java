package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Sales;

public interface SalesDao extends JpaRepository<Sales, Long> {

}
