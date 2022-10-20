package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.pojo.Name;
import jooq.generated.tables.Customer;
import jooq.generated.tables.Employee;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import static jooq.generated.tables.Customer.CUSTOMER;import static jooq.generated.tables.Customerdetail.CUSTOMERDETAIL;import static org.jooq.impl.DSL.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DSLContext ctx;

    public void testUnion() {
        var result = ctx.select(concat(Customer.CUSTOMER.CONTACT_FIRST_NAME, inline(" "),
                        Customer.CUSTOMER.CONTACT_LAST_NAME).as("fullName"))
                .from(Customer.CUSTOMER)
                .union(select(concat(Employee.EMPLOYEE.FIRST_NAME, inline(" "),
                        Employee.EMPLOYEE.LAST_NAME).as("fullName"))
                        .from(Employee.EMPLOYEE))
                .fetchInto(Name.class);

        result.stream().forEach(s -> System.out.println(s));
    }

    public void insertReturningOfCustomerInCustomerDetail() {

        // Note: passing explicit "null" instead of default_() produces implementation specific behaviour

        System.out.println("EXAMPLE 3 (affected rows): "
                + ctx.insertInto(CUSTOMERDETAIL)
                .values(ctx.insertInto(CUSTOMER)
                                .values(default_(),
                                        java.util.UUID.randomUUID().toString(), // random customer_name
                                        "Kyle", "Doyle", "+ 44 321 321",
                                        default_(), default_(), default_())
                                .returningResult(CUSTOMER.CUSTOMER_NUMBER).fetchOne().value1(), //pega o id gerado pelo insert anterior e coloca no proximo
                        java.util.UUID.randomUUID().toString(), // random address_line_first
                        default_(), "Los Angeles", default_(), default_(), "USA")
                .execute()
        );
    }
}
