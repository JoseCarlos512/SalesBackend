package sys_facturation.com.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import sys_facturation.com.entity.IncomeDetail;

public class IncomeDetailDTO {
    private Long idarticulo;

    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    private BigDecimal precio;


    public IncomeDetail toEntity() {
        IncomeDetail detail = new IncomeDetail(idarticulo, null, idarticulo, cantidad, precio);
        detail.setIdarticulo(this.idarticulo);
        detail.setCantidad(this.cantidad);
        detail.setPrecio(this.precio);
        return detail;
    }
}
