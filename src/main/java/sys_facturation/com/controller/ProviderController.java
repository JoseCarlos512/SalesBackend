package sys_facturation.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sys_facturation.com.entity.Provider;
import sys_facturation.com.repository.ProviderDao;


@RestController
@RequestMapping("/proveedores")
public class ProviderController {
    
    @Autowired
	private ProviderDao proovDao;

    @GetMapping("/List")
    public ResponseEntity<List<Provider>> ListProvider() {
        List<Provider> proveedoresList = proovDao.ListProvider();
        return ResponseEntity.ok(proveedoresList);
    }

    @GetMapping("/ById/{id}")
    public ResponseEntity<Provider> ProviderById(@PathVariable Long id) {
        Provider prov = proovDao.ProviderById(id);
        if (prov != null) {
            return ResponseEntity.ok(prov);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/Register")
    public ResponseEntity<Provider> RegisterProvider(@RequestBody Provider proveedor) {
        proovDao.RegisterProvider(proveedor);
        return new ResponseEntity<>(proveedor, HttpStatus.CREATED);
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity<Provider> EditProvider(@PathVariable(value="id") Long id, @RequestBody Provider proveedorDetails) {
        Provider proveedor = proovDao.ProviderById(id);
        if (proveedor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        proveedor.setContacto(proveedorDetails.getContacto());
        // Aquí puedes agregar otros campos a actualizar
        proovDao.RegisterProvider(proveedor);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(value="id") Long id) {
        Provider proveedor = proovDao.ProviderById(id);
        if (proveedor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        proovDao.RemoveProvider(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

