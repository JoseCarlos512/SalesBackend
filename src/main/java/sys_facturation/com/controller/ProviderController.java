package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Provider;
import sys_facturation.com.service.ProviderService;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @GetMapping
    public ResponseEntity<List<Provider>> listAll() {
        List<Provider> list = providerService.ListProvider();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getById(@PathVariable Long id) {
        Provider provider = providerService.ProviderById(id);
        if (provider == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(provider);
    }

    @PostMapping
    public ResponseEntity<Provider> create(@RequestBody Provider provider) {
        Provider saved = providerService.RegisterProvider(provider);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provider> update(@PathVariable Long id, @RequestBody Provider providerDetails) {
        Provider provider = providerService.ProviderById(id);
        if (provider == null) {
            return ResponseEntity.notFound().build();
        }
        provider.setContacto(providerDetails.getContacto());
        provider.setTelefono_contacto(providerDetails.getTelefono_contacto());
        Provider updated = providerService.RegisterProvider(provider);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (providerService.ProviderById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        providerService.RemoveProvider(id);
        return ResponseEntity.noContent().build();
    }
}
