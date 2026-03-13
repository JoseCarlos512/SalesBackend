package sys_facturation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys_facturation.com.entity.Articles;

public interface ArticleDao extends JpaRepository<Articles, Long> {
}
