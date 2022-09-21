package com.github.fabriciolfj.study.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailSum {

    private Integer orderLineNumber;
    private Integer itemsCount;
    private BigDecimal total;

}
