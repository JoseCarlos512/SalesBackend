package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys_facturation.com.entity.Articles;
import sys_facturation.com.repository.ArticleDao;
import sys_facturation.com.service.ArticleService;
import java.util.Collection;

@Service
public class ArticleImplements implements ArticleService {

    @Autowired
    ArticleDao articleRespository;


    @Override
    public void insert(Articles articles) {
        articleRespository.save(articles);
    }

    @Override
    public void update(Articles articles) {
        articleRespository.save(articles);
    }

    @Override
    public Articles findById(Long Id) {
        return articleRespository.findById(Id).orElse(null);
    }

    @Override
    public Collection<Articles> findAll() {
        return (Collection<Articles>) articleRespository.findAll();
    }
}
