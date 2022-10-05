package com.github.fabriciolfj.study.controller;

import com.github.fabriciolfj.entities.Sale;
import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import com.github.fabriciolfj.study.controller.mapper.SaleMapper;
import com.github.fabriciolfj.study.service.SaleManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale")
public class SaleController {

    private final SaleManagementService service;

    /* call jOOQ user-defined DAOs */
    @GetMapping("/saleAscGtLimit")
    public List<SaleDTO> fetchSaleAscGtLimit(@RequestParam int limit) {
        return service.fetchSaleAscGtLimit(limit);
    }

    @GetMapping("/saleByFiscalYear")
    public List<SaleDTO> fetchSaleByFiscalYear(@RequestParam int year) {
        return service.fetchSaleByFiscalYear(year);
    }

    /* call Spring Data DAOs */
    @GetMapping("/top10By")
    public List<SaleDTO> fetchTop10By() {
        return service.findBy10()
                .stream()
                .map(SaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/saleCondition")
    public void columnCondition() {
        service.queryConditionColumn();
    }

    @GetMapping("/subquery")
    public void testSubquery() {
        service.subqueryExample();
    }
}
