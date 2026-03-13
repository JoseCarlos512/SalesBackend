package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.repository.SalesDetailsDao;
import sys_facturation.com.service.SalesDetailsService;

import java.util.List;

@Service
public class SalesDetailsImplements implements SalesDetailsService {

    @Autowired
    private SalesDetailsDao repository;

    @Override
    @Transactional
    public void insert(SalesDetails salesDetails) {
        repository.save(salesDetails);
    }

    @Override
    @Transactional
    public void update(SalesDetails salesDetails) {
        repository.save(salesDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public SalesDetails findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesDetails> findAll() {
        return repository.findAll();
    }
}
