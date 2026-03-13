package sys_facturation.com.service;

import sys_facturation.com.entity.Articles;
import java.util.List;

public interface ArticleService {
    void insert(Articles articles);
    void update(Articles articles);
    Articles findById(Long id);
    List<Articles> findAll();
}
