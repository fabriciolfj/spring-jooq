package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.controller.dto.SaleDTO;
import com.github.fabriciolfj.study.repository.SaleJpaRepository;
import jooq.generated.tables.Order;
import jooq.generated.tables.Sale;
import jooq.generated.tables.daos.SaleRepository;
import jooq.generated.tables.records.SaleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Comparator;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.Tables.*;
import static org.jooq.impl.DSL.*;

@Service
@RequiredArgsConstructor
public class SaleManagementService {

    private final SaleJpaRepository saleJpaRepository;
    private final SaleRepository saleRepository;
    private final DSLContext context;

    @Transactional(propagation = Propagation.REQUIRED)
    public void testInsert() {
        final SaleRecord sr = new SaleRecord();
        sr.setFiscalYear(2022);
        sr.setFiscalMonth(3);
        sr.setEmployeeNumber(1370L);
        sr.setSale(3422.00);
        sr.setEmployeeNumber(1370L);
        sr.setRevenueGrowth(14.55);

        /*ctx.insertInto(Sale.SALE)
                .values(sr.valuesRow().fields()).execute();*/

        //pegando o resultado do insert
        var result = context.insertInto(SALE)
                .set(SALE.FISCAL_YEAR, 2022)
                .set(SALE.SALE_, 400.00)
                .set(SALE.EMPLOYEE_NUMBER, 1370L)
                .set(SALE.REVENUE_GROWTH, 1.33)
                .returningResult(SALE.SALE_ID)
                .fetchOne();

        System.out.println(result);

        //usando o record
        //context.executeInsert(sr);
    }

    public void subqueryExample() {
        var fieldAvg = avg(Sale.SALE.SALE_).coerce(Double.class).as("avgs");
        var fieldSen = Sale.SALE.EMPLOYEE_NUMBER.as("sen");
        var saleTable =
                select(
                        fieldAvg,
                        fieldSen
                    )
                .from(Sale.SALE)
                .groupBy(SALE.EMPLOYEE_NUMBER)
                .asTable("saleTable");

        var result = context.select(
                        Sale.SALE.SALE_ID,
                        Sale.SALE.SALE_,
                        fieldSen
                )
                .from(SALE, saleTable)
                .where(Sale.SALE.EMPLOYEE_NUMBER.eq(saleTable.field(fieldSen))
                        .and(Sale.SALE.SALE_.lessThan(saleTable.field(fieldAvg))))
                .fetch();

        result.forEach(s -> System.out.println(s.get(Sale.SALE.SALE_ID) + " " + s.get(SALE.SALE_) + " " + s.get(fieldSen)));
    }

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

    public void queryConditionColumn() {
        var result = context.select(
                when(Sale.SALE.SALE_.gt(5000.0), "alto").otherwise("baixo")
                .as("saleGT5000"), Sale.SALE.asterisk().except(Sale.SALE.SALE_))
                .from(Sale.SALE)
                .orderBy(Sale.SALE.SALE_ID.desc())
                .limit(10)
                .offset(10)
                .fetch();

        result.forEach(
                s -> System.out.println(s.get("saleGT5000") + " " + s.get(Sale.SALE.SALE_ID))
        );
    }

    public List<SaleDTO> fetchSaleAscGtLimit(double limit) {
        return saleJpaRepository.findSaleAscGtLimit(limit);
    }

    public void findEmployeeSalary60000(boolean isSaleRep) {

        System.out.println("EXAMPLE 11\n"
                + context.select()
                .from(EMPLOYEE)
                .where(EMPLOYEE.SALARY.compare(isSaleRep ? Comparator.IN : Comparator.NOT_IN,
                        select(EMPLOYEE.SALARY).from(EMPLOYEE).where(EMPLOYEE.SALARY.lt(60000))))
                .orderBy(EMPLOYEE.SALARY)
                .fetch()
        );

        System.out.println("EXAMPLE 15.1\n"
                + context.select(PRODUCTLINE.PRODUCT_LINE, PRODUCTLINE.CODE,
                        PRODUCT.PRODUCT_NAME, PRODUCT.QUANTITY_IN_STOCK, PRODUCT.PRODUCT_ID)
                .from(PRODUCTLINE)
                .join(PRODUCT)
                .on(PRODUCT.PRODUCT_ID.eq(select(PRODUCT.PRODUCT_ID).from(PRODUCT)
                        .where(PRODUCTLINE.PRODUCT_LINE.eq(PRODUCT.PRODUCT_LINE))
                        .orderBy(PRODUCT.PRODUCT_ID).limit(1)))
                .fetch()
        );
    }

    public List<com.github.fabriciolfj.entities.Sale> findBy10() {
        return saleJpaRepository.findTop10By();
    }


}
