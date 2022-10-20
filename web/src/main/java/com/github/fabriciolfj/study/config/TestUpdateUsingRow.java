package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Employee;
import jooq.generated.tables.Office;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import static org.jooq.impl.DSL.*;

@Configuration
public class TestUpdateUsingRow implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        context.update(Office.OFFICE)
                .set(row(Office.OFFICE.ADDRESS_LINE_FIRST, Office.OFFICE.ADDRESS_LINE_SECOND, Office.OFFICE.PHONE),
                        select(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME, val("16933388"))
                                .from(Employee.EMPLOYEE)
                                .where(Employee.EMPLOYEE.JOB_TITLE.eq("President")))
                .execute();
    }
}
