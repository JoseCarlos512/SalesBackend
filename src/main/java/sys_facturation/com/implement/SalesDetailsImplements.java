package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.repository.SalesDetailsDao;
import sys_facturation.com.service.SalesDetailsService;
import java.util.Collection;

@Service
public class SalesDetailsImplements implements SalesDetailsService {

    @Autowired
    SalesDetailsDao repository;

    @Override
    public void insert(SalesDetails salesDetails) {
        repository.save(salesDetails);
    }

    @Override
    public void update(SalesDetails salesDetails) {
        repository.save(salesDetails);
    }

    @Override
    public SalesDetails findById(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    @Override
    public Collection<SalesDetails> findAll() {
        return (Collection<SalesDetails>) repository.findAll();
    }
}
