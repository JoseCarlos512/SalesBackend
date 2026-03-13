package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Person;
import sys_facturation.com.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> listAll() {
        List<Person> list = personService.ListPerson();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        Person person = personService.PersonById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person saved = personService.RegisterPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person personDetails) {
        Person person = personService.PersonById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        person.setNombre(personDetails.getNombre());
        person.setTipo_documento(personDetails.getTipo_documento());
        person.setNum_documento(personDetails.getNum_documento());
        person.setDireccion(personDetails.getDireccion());
        person.setTelefono(personDetails.getTelefono());
        person.setEmail(personDetails.getEmail());
        Person updated = personService.RegisterPerson(person);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (personService.PersonById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        personService.RemovePerson(id);
        return ResponseEntity.noContent().build();
    }
}
