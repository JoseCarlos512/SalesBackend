package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.Categories;
import sys_facturation.com.repository.CategorieDao;
import sys_facturation.com.service.CategoriesService;

import java.util.List;

@Service
public class CategoriesImplements implements CategoriesService {

    @Autowired
    private CategorieDao categorieDao;

    @Override
    @Transactional
    public void insert(Categories categories) {
        categorieDao.save(categories);
    }

    @Override
    @Transactional
    public void update(Categories categories) {
        categorieDao.save(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categories> findAll() {
        return categorieDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Categories findById(Long id) {
        return categorieDao.findById(id).orElse(null);
    }
}
