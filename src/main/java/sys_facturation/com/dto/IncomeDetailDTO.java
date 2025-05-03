package sys_facturation.com.dto;

import java.math.BigDecimal;

import sys_facturation.com.entity.Income;
import sys_facturation.com.entity.IncomeDetail;

public class IncomeDetailDTO {
    public Long idarticulo;
    public Integer cantidad;
    public BigDecimal precio;


    public IncomeDetail toEntity(Income income) {
        IncomeDetail detail = new IncomeDetail();
        detail.setIncome(income);
        detail.setIdarticulo(this.idarticulo);
        detail.setCantidad(this.cantidad);
        detail.setPrecio(this.precio);
        return detail;
    }
}
