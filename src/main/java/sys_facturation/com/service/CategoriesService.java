package sys_facturation.com.service;

import sys_facturation.com.entity.Categories;
import java.util.List;

public interface CategoriesService {
    void insert(Categories categories);
    void update(Categories categories);
    List<Categories> findAll();
    Categories findById(Long id);
}
