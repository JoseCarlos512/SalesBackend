package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.dto.RolDTO;
import sys_facturation.com.entity.Rol;
import sys_facturation.com.service.RolService;
import java.util.Collection;

@RestController
@RequestMapping("")
public class RolController {

    @Autowired
    RolService rolService;
    @GetMapping("/rol")
    public ResponseEntity<?> List() {
        Collection<RolDTO> roles = rolService.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR: Rol not found");
        }
        return ResponseEntity.ok(roles);
    }
    @GetMapping("/rol/{id}")
    public ResponseEntity<RolDTO> FindById(@PathVariable Long id) {
        RolDTO rol = rolService.findById(id);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rol, HttpStatus.OK);
    }
    @PostMapping("/rol")
    public ResponseEntity<RolDTO> Insert(@RequestBody Rol rol) {
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RolDTO createdRol = rolService.insert(rol);
        return new ResponseEntity<>(createdRol, HttpStatus.CREATED);
    }

    @PutMapping("/rol/{id}")
    public ResponseEntity<RolDTO> Update(@PathVariable(value="id") Long id, @RequestBody Rol rol) {
        if (rol == null || id == null || !id.equals(rol.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RolDTO updatedRol = rolService.update(rol);
        if (updatedRol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRol, HttpStatus.OK);
    }

    @DeleteMapping("/rol/{id}")
    public ResponseEntity<Void> Delete(@PathVariable(value="id") Long id) {
        Boolean deleted = rolService.deleteById(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
