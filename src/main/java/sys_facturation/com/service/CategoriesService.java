package sys_facturation.com.service;

import org.hibernate.sql.ast.tree.expression.Collation;
import sys_facturation.com.entity.Categories;

import java.util.Collection;

public interface CategoriesService {

    abstract void insert(Categories categories);
    abstract void update(Categories categories);
    abstract Collection<Categories> findAll();
    abstract Categories findById(Long Id);
}
