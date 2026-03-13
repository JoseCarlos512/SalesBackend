package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Categories;

public interface CategorieDao extends JpaRepository<Categories, Long> {
}
