package com.github.fabriciolfj.study.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SaleDTO {

    public Integer fiscalYear;
    private Double sale;
    private String employee;
}
