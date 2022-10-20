package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Employee;
import jooq.generated.tables.Product;
import jooq.generated.tables.Sale;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.val;

//@Configuration
public class TestMerge implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        context.mergeInto(Product.PRODUCT)
                .usingDual()
                .on(Product.PRODUCT.PRODUCT_NAME.eq("1952 Alpine Renault 1300"))
                .whenMatchedThenUpdate() //se corresponder a condicao acima, faça update
                .set(Product.PRODUCT.PRODUCT_NAME, "1952 Alpine Renault 1600")
                .whenNotMatchedThenInsert( //se nao, faça insert
                        Product.PRODUCT.PRODUCT_NAME, Product.PRODUCT.CODE
                ).values("1952 Alpine Renault 1600", 599302L)
                .execute();

        context.mergeInto(Sale.SALE)
                .using(Employee.EMPLOYEE)
                .on(Employee.EMPLOYEE.EMPLOYEE_NUMBER.eq(Sale.SALE.EMPLOYEE_NUMBER))
                .whenMatchedThenDelete() //achou, deleto
                .whenNotMatchedThenInsert( //se nao achou, insere
                        Sale.SALE.EMPLOYEE_NUMBER,
                        Sale.SALE.FISCAL_YEAR,
                        Sale.SALE.SALE_,
                        Sale.SALE.FISCAL_MONTH,
                        Sale.SALE.REVENUE_GROWTH)
                .values(Employee.EMPLOYEE.EMPLOYEE_NUMBER,
                        val(2015),
                        coalesce(val(-1.0).mul(Employee.EMPLOYEE.COMMISSION), val(0.0)),
                        val(1),
                        val(0.0))
                .execute();
    }
}
