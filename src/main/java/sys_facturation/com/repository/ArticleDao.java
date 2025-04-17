package sys_facturation.com.repository;

import org.springframework.data.repository.CrudRepository;
import sys_facturation.com.entity.Articles;

public interface ArticleDao extends CrudRepository<Articles, Long> {
}
