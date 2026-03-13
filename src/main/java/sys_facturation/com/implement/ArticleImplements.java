package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.Articles;
import sys_facturation.com.repository.ArticleDao;
import sys_facturation.com.service.ArticleService;

import java.util.List;

@Service
public class ArticleImplements implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional
    public void insert(Articles articles) {
        articleDao.save(articles);
    }

    @Override
    @Transactional
    public void update(Articles articles) {
        articleDao.save(articles);
    }

    @Override
    @Transactional(readOnly = true)
    public Articles findById(Long id) {
        return articleDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Articles> findAll() {
        return articleDao.findAll();
    }
}
