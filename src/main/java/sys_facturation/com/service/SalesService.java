package sys_facturation.com.service;

import sys_facturation.com.entity.Sales;
import java.util.List;

public interface SalesService {
    void insert(Sales sales);
    void update(Sales sales);
    Sales findById(Long id);
    List<Sales> findAll();
}
