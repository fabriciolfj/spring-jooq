package com.github.fabriciolfj.study.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {

    private String customerName;
    private String city;
    private BigDecimal creditLimit;
}
