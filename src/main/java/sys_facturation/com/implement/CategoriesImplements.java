package sys_facturation.com.implement;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys_facturation.com.repository.CategorieDao;
import sys_facturation.com.service.CategoriesService;

import java.util.Collection;

@Service
public class CategoriesImplements implements CategoriesService {

    @Autowired
    CategorieDao categoriesRepository;

    @Override
    public void insert(Categories categories) {
        categoriesRepository.save(categories);
    }

    @Override
    public void update(Categories categories) {
        categoriesRepository.save(categories);
    }

    @Override
    public Collection<Categories> findAll() {
        return (Collection<Categories>) categoriesRepository.findAll();
    }

    @Override
    public Categories findById(Long Id) {
        return categoriesRepository.findById(Id).orElse(null);
    }
}
