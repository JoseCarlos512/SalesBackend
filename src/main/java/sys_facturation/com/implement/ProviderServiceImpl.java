package sys_facturation.com.implement;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sys_facturation.com.entity.Provider;
import sys_facturation.com.repository.ProviderDao;

@Repository
public class ProviderServiceImpl implements ProviderDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Provider> ListProvider() {
        return em.createQuery("FROM Provider", Provider.class).getResultList();
    }

    @Override
    @Transactional
    public Provider RegisterProvider(Provider prov) {
        if (prov.getId() == null) {
            em.persist(prov);
        } else {
            em.merge(prov);
        }
        return prov;
    }

    @Override
    @Transactional
    public Provider EditProvider(Provider prov) {
        return em.merge(prov);
    }

    @Override
    @Transactional
    public Provider ProviderById(Long id) {
        return em.find(Provider.class, id);
    }

    @Override
    @Transactional
    public void RemoveProvider(Long id) {
        Provider prov = ProviderById(id);
        if (prov != null) {
            em.remove(prov);
        }
    }

   
    
}
