package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Customer;
import jooq.generated.tables.Employee;
import jooq.generated.tables.Sale;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static org.jooq.impl.DSL.*;

@Configuration
public class TestUpdateUsingReturn implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        context.update(Customer.CUSTOMER)
                .set(Customer.CUSTOMER.CREDIT_LIMIT, Customer.CUSTOMER.CREDIT_LIMIT.plus(
                        context.update(Employee.EMPLOYEE)
                                .set(Employee.EMPLOYEE.SALARY, Employee.EMPLOYEE.SALARY.plus(
                                        field(select(avg(Sale.SALE.SALE_)).from(Sale.SALE)
                                                .where(Sale.SALE.EMPLOYEE_NUMBER.eq(Employee.EMPLOYEE.EMPLOYEE_NUMBER)))))
                                .where(Employee.EMPLOYEE.EMPLOYEE_NUMBER.eq(1504L))
                                .returningResult(Employee.EMPLOYEE.SALARY.coerce(BigDecimal.class))
                                .fetchOne().value1()
                                .multiply(BigDecimal.valueOf(2))
                ))
                .where(Customer.CUSTOMER.SALES_REP_EMPLOYEE_NUMBER.eq(1504L))
                .execute();
    }
}
