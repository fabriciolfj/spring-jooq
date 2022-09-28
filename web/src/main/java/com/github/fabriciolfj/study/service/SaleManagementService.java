package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import com.github.fabriciolfj.study.repository.SaleJpaRepository;
import jooq.generated.tables.daos.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleManagementService {

    private final SaleJpaRepository saleJpaRepository;
    private final SaleRepository saleRepository;

    @Transactional(readOnly = true)
    public List<SaleDTO> fetchSaleByFiscalYear(int year) {
        return saleRepository.fetchByFiscalYear(year)
                .stream()
                .map(s -> SaleDTO.builder()
                        .sale(s.getSale())
                        .fiscalYear(s.getFiscalYear())
                        .employee(s.getEmployeeNumber().toString())
                        .build())
                .collect(Collectors.toList());
        //return saleJpaRepository.findSaleByFiscalYear(year);
    }

    public List<SaleDTO> fetchSaleAscGtLimit(double limit) {
        return saleJpaRepository.findSaleAscGtLimit(limit);
    }

    public List<com.github.fabriciolfj.entities.Sale> findBy10() {
        return saleJpaRepository.findTop10By();
    }
}
