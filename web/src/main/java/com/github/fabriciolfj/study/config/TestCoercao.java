package com.github.fabriciolfj.study.config;

import com.github.fabriciolfj.study.pojo.EmployeeName;
import jooq.generated.tables.Employee;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestCoercao implements CommandLineRunner {

    @Autowired
    private DSLContext ctx;

    @Override
    public void run(String... args) throws Exception {
        //testOne();
    }

    public void testOne() {
        var result = ctx.resultQuery("Select first_name, last_name from employee")
                .coerce(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME)
                .fetchInto(EmployeeName.class);

        result.forEach(System.out::println);
    }
}
