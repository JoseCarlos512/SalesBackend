package sys_facturation.com.service;

import java.util.List;
import java.util.Optional;

import sys_facturation.com.entity.Income;

public interface IncomeService {
    public List<Income> findAll();

    public Optional<Income> findById(Long id);

    public Income save(Income ingreso);
    
    public void deleteById(Long id);
}
