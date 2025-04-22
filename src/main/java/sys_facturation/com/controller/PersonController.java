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

import sys_facturation.com.entity.Person;
import sys_facturation.com.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
	private PersonService personaService;

    @GetMapping("/List")
    public ResponseEntity<List<Person>> ListPerson() {
        List<Person> personasList = personaService.ListPerson();
        return new ResponseEntity<>(personasList, HttpStatus.OK);
    }
    
    @GetMapping("/ById/{id}")
    public ResponseEntity<Person> PersonById(@PathVariable Long id) {
        Person person = personaService.PersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/Register")
    public ResponseEntity<Person> RegisterPerson(@RequestBody Person persona) {
        personaService.RegisterPerson(persona);
        return new ResponseEntity<>(persona, HttpStatus.CREATED);
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity<Person> EditPerson(@PathVariable(value="id") Long id, @RequestBody Person personaDetails) {
        Person persona = personaService.PersonById(id);
        if (persona == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        persona.setNombre(personaDetails.getNombre());
        // Aquí puedes agregar otros campos a actualizar
        personaService.RegisterPerson(persona);
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }

    @DeleteMapping("/Remove/{id}")
    public ResponseEntity<Void> RemovePerson(@PathVariable(value="id") Long id) {
        Person persona = personaService.PersonById(id);
        if (persona == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        personaService.RemovePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}