package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.SalesDetails;

public interface SalesDetailsDao extends JpaRepository<SalesDetails, Long> {

}
