package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;
import sys_facturation.com.entity.Sales;

public interface SalesDao extends CrudRepository<Sales,Long> {

}
