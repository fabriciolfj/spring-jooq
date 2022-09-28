package com.github.fabriciolfj.study.controller.mapper;

import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import jooq.generated.tables.Sale;

public class SaleMapper {

    private SaleMapper() { }

    public static SaleDTO toDTO(final Sale sale) {
        return SaleDTO.builder()
                .employee(sale.employee().getName())
                .fiscalYear(sale.newRecord().getFiscalYear())
                .sale(sale.newRecord().getSale())
                .build();
    }

    public static SaleDTO toDTO(final com.github.fabriciolfj.entities.Sale sale) {
        return SaleDTO.builder()
                .fiscalYear(sale.getFiscalYear())
                .employee(sale.getEmployee().getFirstName())
                .sale(sale.getSale().doubleValue())
                .build();
    }
}
