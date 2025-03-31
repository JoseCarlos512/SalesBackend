package sys_facturation.com.repository;

import java.util.List;

import sys_facturation.com.entity.Provider;

public interface ProviderDao {
    public List<Provider> ListProvider();
	
	public Provider RegisterProvider(Provider prov);

	public Provider EditProvider(Provider prov);
	
	public Provider ProviderById(Long id);
	
	public void RemoveProvider(Long id);
}
