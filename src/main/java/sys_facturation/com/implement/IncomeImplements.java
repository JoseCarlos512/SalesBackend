package sys_facturation.com.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.dto.IncomeDTO;
import sys_facturation.com.entity.Income;
import sys_facturation.com.repository.IncomeDao;
import sys_facturation.com.service.IncomeService;
import sys_facturation.com.util.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeImplements implements IncomeService {

    private static final Logger log = LoggerFactory.getLogger(IncomeImplements.class);

    @Autowired
    private IncomeDao incomeDao;

    @Override
    @Transactional(readOnly = true)
    public List<Income> findAll() {
        return incomeDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Income> findById(Long id) {
        return incomeDao.findById(id);
    }

    @Override
    @Transactional
    public IncomeDTO save(Income income) {
        log.debug("Guardando ingreso: proveedor={}, estado={}", income.getIdproveedor(), income.getEstado());
        Income saved = incomeDao.save(income);
        return MapperUtils.toIncomeDTO(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        incomeDao.deleteById(id);
    }
}
