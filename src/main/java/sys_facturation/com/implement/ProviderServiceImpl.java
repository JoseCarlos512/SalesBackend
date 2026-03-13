package sys_facturation.com.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_facturation.com.entity.Provider;
import sys_facturation.com.repository.ProviderDao;
import sys_facturation.com.service.ProviderService;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderDao providerDao;

    @Override
    @Transactional(readOnly = true)
    public List<Provider> ListProvider() {
        return providerDao.findAll();
    }

    @Override
    @Transactional
    public Provider RegisterProvider(Provider prov) {
        return providerDao.save(prov);
    }

    @Override
    @Transactional
    public Provider EditProvider(Provider prov) {
        return providerDao.save(prov);
    }

    @Override
    @Transactional(readOnly = true)
    public Provider ProviderById(Long id) {
        return providerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void RemoveProvider(Long id) {
        providerDao.deleteById(id);
    }
}
