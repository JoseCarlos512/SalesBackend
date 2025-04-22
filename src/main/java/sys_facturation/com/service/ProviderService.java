package sys_facturation.com.service;

import java.util.List;

import sys_facturation.com.entity.Provider;

public interface ProviderService {
    public List<Provider> ListProvider();
	
	public Provider RegisterProvider(Provider prov);

	public Provider EditProvider(Provider prov);
	
	public Provider ProviderById(Long id);
	
	public void RemoveProvider(Long id);
}
