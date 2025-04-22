package sys_facturation.com.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import sys_facturation.com.entity.Income;
import sys_facturation.com.entity.IncomeDetail;

public class IncomeDTO {
    private Long idproveedor;

    private Long idusuario;

    private String tipo_comprobante;

    private String serie_comprobante;
    private String num_comprobante;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String estado = "ACTIVO";

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha_hora;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updated_at;

    @Valid
    @NotEmpty(message = "Debe incluir al menos un detalle")
    private List<IncomeDetailDTO> detalles;


    public Income toEntity() {
        Income income = new Income();
        income.setIdproveedor(this.idproveedor);
        income.setIdusuario(this.idusuario);
        income.setTipo_comprobante(this.tipo_comprobante);
        income.setSerie_comprobante(this.serie_comprobante);
        income.setNum_comprobante(this.num_comprobante);
        income.setImpuesto(this.impuesto);
        income.setTotal(this.total);
        income.setEstado(this.estado);
        income.setFecha_hora(this.fecha_hora);
        income.setCreated_at(this.created_at != null ? this.created_at : LocalDateTime.now());
        income.setUpdated_at(LocalDateTime.now());

        if (this.detalles != null) {
            List<IncomeDetail> detailsEntities = this.detalles.stream()
                .map(dto -> {
                    IncomeDetail detail = dto.toEntity();
                    detail.setIncome(income);
                    return detail;
                })
                .toList();
            income.setDetalles(detailsEntities);
        }

        return income;
    }
}
