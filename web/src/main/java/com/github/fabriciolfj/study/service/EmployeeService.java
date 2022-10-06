package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.pojo.Name;
import jooq.generated.tables.Customer;
import jooq.generated.tables.Employee;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.*;

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
}
