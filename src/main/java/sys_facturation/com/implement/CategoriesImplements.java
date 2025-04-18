package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys_facturation.com.entity.Categories;
import sys_facturation.com.repository.CategorieDao;
import sys_facturation.com.service.CategoriesService;
import java.util.Collection;


@Service
public class CategoriesImplements implements CategoriesService {

    @Autowired
    CategorieDao categorieDao;


    @Override
    public void insert(Categories categories) {
        categorieDao.save(categories);
    }

    @Override
    public void update(Categories categories) {
        categorieDao.save(categories);
    }

    @Override
    public Collection<Categories> findAll() {
        return (Collection<Categories>) categorieDao.findAll();
    }

    @Override
    public Categories findById(Long Id) {
        return categorieDao.findById(Id).orElse(null);
    }
}
