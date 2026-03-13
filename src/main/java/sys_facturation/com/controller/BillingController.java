package sys_facturation.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys_facturation.com.dto.BillingResponseDTO;
import sys_facturation.com.sunat.SunatBillingService;

/**
 * Endpoint para enviar comprobantes electrónicos a SUNAT.
 *
 * Flujo típico del frontend:
 *   1. POST /sales          → registra la venta (estado = PENDIENTE)
 *   2. POST /billing/{id}   → genera XML, firma y envía a SUNAT
 */
@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private SunatBillingService billingService;

    /**
     * Sends the specified sale to SUNAT as an electronic voucher.
     * Updates the sale's estado and sunat fields with the CDR result.
     */
    @PostMapping("/{saleId}")
    public ResponseEntity<BillingResponseDTO> sendToSunat(@PathVariable Long saleId) {
        BillingResponseDTO result = billingService.send(saleId);
        if ("ERROR".equals(result.getEstado())) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
