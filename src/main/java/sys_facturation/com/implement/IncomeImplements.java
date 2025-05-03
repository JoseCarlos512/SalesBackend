package sys_facturation.com.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sys_facturation.com.dto.IncomeDTO;
import sys_facturation.com.entity.Income;
import sys_facturation.com.entity.IncomeDetail;
import sys_facturation.com.entity.User;
import sys_facturation.com.repository.IncomeDao;
import sys_facturation.com.service.IncomeService;
import sys_facturation.com.util.MapperUtils;

@Repository
public class IncomeImplements implements IncomeService {

    @Autowired
    private IncomeDao incomeDao;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Income> findAll() {
        return em.createQuery("FROM Income", Income.class).getResultList();
    }

    @Override
    public Optional<Income> findById(Long id) {
        return incomeDao.findById(id);
    }

    @Override
    @Transactional
    public IncomeDTO save(Income income) {
        System.out.println(">>>>> Fecha recibida: " + income.getFechaHora());
        System.out.println(">>>>> ESTADO RECIBIDO: " + income.getEstado());
        Income saved = incomeDao.save(income);
        return MapperUtils.toIncomeDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        incomeDao.deleteById(id);
    }

}
