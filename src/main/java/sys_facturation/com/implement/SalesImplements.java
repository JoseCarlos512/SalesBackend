package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.repository.SalesDao;
import sys_facturation.com.service.SalesService;

import java.util.List;

@Service
public class SalesImplements implements SalesService {

    @Autowired
    private SalesDao repository;

    @Override
    @Transactional
    public void insert(Sales sales) {
        repository.save(sales);
    }

    @Override
    @Transactional
    public void update(Sales sales) {
        repository.save(sales);
    }

    @Override
    @Transactional(readOnly = true)
    public Sales findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sales> findAll() {
        return repository.findAll();
    }
}
