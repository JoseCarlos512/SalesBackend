package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.repository.SalesDao;
import sys_facturation.com.service.SalesService;
import java.util.Collection;

@Service
public class SalesImplements implements SalesService {

    @Autowired
    SalesDao repository;

    @Override
    public void insert(Sales sales) {
        repository.save(sales);
    }

    @Override
    public void update(Sales sales) {
        repository.save(sales);
    }

    @Override
    public Sales findById(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    @Override
    public Collection<Sales> findAll() {
        return (Collection<Sales>) repository.findAll();
    }
}
