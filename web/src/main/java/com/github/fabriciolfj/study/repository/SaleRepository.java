package com.github.fabriciolfj.study.repository;

import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import java.util.List;

public interface SaleRepository {

    List<SaleDTO> findSaleByFiscalYear(int year);
    List<SaleDTO> findSaleAscGtLimit(double limit);
}
