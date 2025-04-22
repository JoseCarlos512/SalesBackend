package sys_facturation.com.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sys_facturation.com.entity.Income;
import sys_facturation.com.entity.IncomeDetail;
import sys_facturation.com.repository.IncomeDao;
import sys_facturation.com.service.IncomeService;

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
    public Income save(Income income) {
        for (IncomeDetail detalle : income.getDetalles()) {
            detalle.setIncome(income);
        }
        System.out.println(">>>>> Fecha recibida: " + income.getFecha_hora());
        return incomeDao.save(income);
    }

    @Override
    public void deleteById(Long id) {
        incomeDao.deleteById(id);
    }

}
