package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;
import sys_facturation.com.entity.Categories;

public interface CategorieDao extends CrudRepository<Categories, Long> {
}
