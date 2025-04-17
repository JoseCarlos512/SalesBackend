package sys_facturation.com.service;

import java.util.Collection;

public interface ArticleService {

    abstract void insert(Articles articles);
    abstract void update(Articles articles);
    abstract Articles findById(Long Id);
    Collection<Articles> findAll();

}
