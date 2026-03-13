package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.dto.IncomeDTO;
import sys_facturation.com.entity.Income;
import sys_facturation.com.service.IncomeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<Income>> listAll() {
        List<Income> list = incomeService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return incomeService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Ingreso con ID " + id + " no encontrado.")));
    }

    @PostMapping
    public ResponseEntity<IncomeDTO> create(@RequestBody IncomeDTO dto) {
        Income income = dto.toEntity();
        IncomeDTO saved = incomeService.save(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (incomeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        incomeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
