package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.Sales;
import sys_facturation.com.service.SalesService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = {"*"})

public class SalesController {

    @Autowired
    SalesService salesService;


    // ----------METHOD LIST ALL SALES--------------
    // LINK FOR DEVELOPMENT :
    // LINK FOR PRODUCTION :

   @GetMapping("/listAll")
    public ResponseEntity<?> listAll(){
        Collection<Sales> listAll = salesService.findAll();
        if(listAll.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR: Sales not found");
        }
        return  ResponseEntity.ok(listAll);
    }


    // ----------METHOD SEARCH SALES FOR ID--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        try {
            Sales sale = salesService.findById(id);
            if (sale == null) {
                return ResponseEntity
                        .status(404)
                        .body("La venta con ID " + id + " no fue encontrada.");
            }
            return ResponseEntity.ok(sale);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error al buscar la venta: " + e.getMessage());
        }
    }


    // ----------METHOD INSERT SALES--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Sales sales) {
        Map<String, Object> response = new HashMap<>();
        try {
            salesService.insert(sales);
            response.put("Datos de venta: ", sales);
            response.put("message", "Venta creada correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
        } catch (Exception e) {
            response.put("error", "Error al crear venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }

    // ----------METHOD UPDATE SALES--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @PutMapping("/update_article/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Sales newSales) {
        Map<String, Object> response = new HashMap<>();
        Sales sales = salesService.findById(id);
        if (sales != null) {

            sales.setNumComprobante(newSales.getNumComprobante());
            sales.setSerieComprobante(newSales.getSerieComprobante());
            sales.setTipoComprobante(newSales.getTipoComprobante());
            sales.setImpuesto(newSales.getImpuesto());
            sales.setTotal(newSales.getTotal());
            sales.setEstado(newSales.getEstado());

            salesService.update(sales);
            response.put("Mensaje", "Venta actualizada correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error: ", "No se encuentra venta con ID" + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
