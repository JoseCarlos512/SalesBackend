package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Provider;

public interface ProviderDao extends JpaRepository<Provider, Long> {
}
