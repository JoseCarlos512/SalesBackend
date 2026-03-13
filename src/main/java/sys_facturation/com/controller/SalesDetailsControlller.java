package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.service.SalesDetailsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales-details")
public class SalesDetailsControlller {

    @Autowired
    private SalesDetailsService salesDetailsService;

    @GetMapping
    public ResponseEntity<List<SalesDetails>> listAll() {
        List<SalesDetails> list = salesDetailsService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        SalesDetails detail = salesDetailsService.findById(id);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Detalle con ID " + id + " no encontrado."));
        }
        return ResponseEntity.ok(detail);
    }

    @PostMapping
    public ResponseEntity<SalesDetails> create(@RequestBody SalesDetails salesDetails) {
        salesDetailsService.insert(salesDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(salesDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SalesDetails newData) {
        SalesDetails detail = salesDetailsService.findById(id);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Detalle con ID " + id + " no encontrado."));
        }
        detail.setCantidad(newData.getCantidad());
        detail.setDescuento(newData.getDescuento());
        detail.setPrecio(newData.getPrecio());
        detail.setSales(newData.getSales());
        salesDetailsService.update(detail);
        return ResponseEntity.ok(detail);
    }
}
