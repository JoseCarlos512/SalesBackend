package sys_facturation.com.service;


import sys_facturation.com.entity.SalesDetails;
import java.util.Collection;

public interface SalesDetailsService {

    abstract void insert(SalesDetails salesDetails);
    abstract void update(SalesDetails salesDetails);
    abstract SalesDetails findById(Long Id);
    Collection<SalesDetails> findAll();

}
