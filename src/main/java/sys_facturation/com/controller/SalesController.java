package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.service.SalesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ResponseEntity<List<Sales>> listAll() {
        List<Sales> list = salesService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Sales sale = salesService.findById(id);
        if (sale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Venta con ID " + id + " no encontrada."));
        }
        return ResponseEntity.ok(sale);
    }

    @PostMapping
    public ResponseEntity<Sales> create(@RequestBody Sales sales) {
        // Ensure each detail has the back-reference set before cascade save
        if (sales.getDetalles() != null) {
            for (SalesDetails detail : sales.getDetalles()) {
                detail.setSales(sales);
            }
        }
        salesService.insert(sales);
        return ResponseEntity.status(HttpStatus.CREATED).body(sales);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Sales newData) {
        Sales sale = salesService.findById(id);
        if (sale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Venta con ID " + id + " no encontrada."));
        }
        sale.setNumComprobante(newData.getNumComprobante());
        sale.setSerieComprobante(newData.getSerieComprobante());
        sale.setTipoComprobante(newData.getTipoComprobante());
        sale.setImpuesto(newData.getImpuesto());
        sale.setTotal(newData.getTotal());
        sale.setEstado(newData.getEstado());
        sale.setPerson(newData.getPerson());
        salesService.update(sale);
        return ResponseEntity.ok(sale);
    }
}
