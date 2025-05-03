package sys_facturation.com.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sys_facturation.com.dto.IncomeDTO;
import sys_facturation.com.entity.Income;
import sys_facturation.com.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @GetMapping("/List")
    public ResponseEntity<List<Income>> getAll() {
        List<Income> all = incomeService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/ById/{id}")
    public Optional<Income> getById(@PathVariable Long id) {
        return incomeService.findById(id);
    }

    @PostMapping("/Register")
    public ResponseEntity<IncomeDTO> create(@RequestBody IncomeDTO dto) {
        Income income = dto.toEntity();
        IncomeDTO saved = incomeService.save(income);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public ResponseEntity<IncomeDTO> test(@RequestBody Map<String, Object> request) {
        System.out.println("Request map: " + request);
        IncomeDTO dto = new IncomeDTO();
        dto.setEstado((String) request.get("estado"));
        // Asigna otros campos...
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/Remove/{id}")
    public void delete(@PathVariable Long id) {
        incomeService.deleteById(id);
    }
}
