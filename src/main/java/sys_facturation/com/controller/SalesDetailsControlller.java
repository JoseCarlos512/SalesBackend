package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.entity.SalesDetails;
import sys_facturation.com.service.SalesDetailsService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sales_details")
@CrossOrigin(origins = {"*"})

public class SalesDetailsControlller {

    @Autowired
    SalesDetailsService salesDetailsService;

    // ----------METHOD LIST ALL SALES--------------
    // LINK FOR DEVELOPMENT :
    // LINK FOR PRODUCTION :

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(){
        Collection<SalesDetails> listAll = salesDetailsService.findAll();
        if(listAll.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ERROR: Sales details not found");
        }
        return  ResponseEntity.ok(listAll);
    }


    // ----------METHOD SEARCH SALES DETAILS FOR ID--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        try {
            SalesDetails salesDetails = salesDetailsService.findById(id);
            if (salesDetails == null) {
                return ResponseEntity
                        .status(404)
                        .body("Detalle con ID: " + id + " no fue encontrada.");
            }
            return ResponseEntity.ok(salesDetails);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error al buscar el detalle de venta: " + e.getMessage());
        }
    }


    // ----------METHOD INSERT SALES DETAILS--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody SalesDetails salesDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            salesDetailsService.insert(salesDetails);
            response.put("Datos detalle de venta: ", salesDetails);
            response.put("message", "Detalle de venta creada correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
        } catch (Exception e) {
            response.put("error", "Error al crear detalle de venta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }

    // ----------METHOD UPDATE SALES DETAILS--------------
    // LINK FOR DEVELOPMENT:
    // LINK FOR PRODUCTION:

    @PutMapping("/update_details/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SalesDetails newSalesDetails) {
        Map<String, Object> response = new HashMap<>();
        SalesDetails salesDetails = salesDetailsService.findById(id);
        if (salesDetails != null) {

            salesDetails.setCantidad(newSalesDetails.getCantidad());
            salesDetails.setDescuento(newSalesDetails.getDescuento());
            salesDetails.setPrecio(newSalesDetails.getPrecio());
            // FK
            salesDetails.setSales(newSalesDetails.getSales());

            salesDetailsService.update(salesDetails);
            response.put("Mensaje: ", "Detalle de venta actualizado");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error: ", "No se encuentra detalle de venta con ID" + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
