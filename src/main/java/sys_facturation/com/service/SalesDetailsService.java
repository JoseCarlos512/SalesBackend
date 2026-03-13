package sys_facturation.com.service;

import sys_facturation.com.entity.SalesDetails;
import java.util.List;

public interface SalesDetailsService {
    void insert(SalesDetails salesDetails);
    void update(SalesDetails salesDetails);
    SalesDetails findById(Long id);
    List<SalesDetails> findAll();
}
