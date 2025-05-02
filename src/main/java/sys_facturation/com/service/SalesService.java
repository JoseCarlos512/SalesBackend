package sys_facturation.com.service;

import sys_facturation.com.entity.Sales;
import java.util.Collection;

public interface SalesService {

    abstract void insert(Sales sales);
    abstract void update(Sales sales);
    abstract Sales findById(Long Id);
    Collection<Sales> findAll();
}
