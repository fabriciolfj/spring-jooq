package com.github.fabriciolfj.study.repository.impl;

import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import com.github.fabriciolfj.study.repository.SaleRepository;
import jooq.generated.tables.Employee;
import jooq.generated.tables.Sale;
import jooq.generated.tables.records.SaleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class SaleRepositoryImpl implements SaleRepository {

    private final DSLContext ctx;

    @Override
    public List<SaleDTO> findSaleByFiscalYear(int year) {
        return ctx.select(Sale.SALE.FISCAL_YEAR, Sale.SALE.SALE_, Employee.EMPLOYEE.FIRST_NAME)
                .from(Sale.SALE)
                .innerJoin(Employee.EMPLOYEE)
                .on(Sale.SALE.EMPLOYEE_NUMBER.eq(Employee.EMPLOYEE.EMPLOYEE_NUMBER))
                .where(Sale.SALE.FISCAL_YEAR.eq(year))
                .fetch()
                .map(s -> SaleDTO.builder()
                        .sale(s.get(Sale.SALE.SALE_))
                        .fiscalYear(s.get(Sale.SALE.FISCAL_YEAR))
                        .employee(s.get(Employee.EMPLOYEE.FIRST_NAME))
                        .build())
                .stream().toList();
    }

    @Override
    public List<SaleDTO> findSaleAscGtLimit(double limit) {
        return ctx.select(Sale.SALE.FISCAL_YEAR, Sale.SALE.SALE_, Employee.EMPLOYEE.FIRST_NAME)
                .from(Sale.SALE)
                .innerJoin(Employee.EMPLOYEE)
                .on(Sale.SALE.EMPLOYEE_NUMBER.eq(Employee.EMPLOYEE.EMPLOYEE_NUMBER))
                .where(Sale.SALE.SALE_.ge(limit))
                .orderBy(Sale.SALE.SALE_)
                .fetch()
                .map(s -> SaleDTO.builder()
                        .sale(s.get(Sale.SALE.SALE_))
                        .fiscalYear(s.get(Sale.SALE.FISCAL_YEAR))
                        .employee(s.get(Employee.EMPLOYEE.FIRST_NAME))
                        .build())
                .stream().toList();
    }
}
