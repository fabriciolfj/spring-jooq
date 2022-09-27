package com.github.fabriciolfj.study.config;


import com.github.fabriciolfj.study.pojo.EmployeeName;
import jooq.generated.tables.Employee;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import static org.jooq.impl.DSL.concat;
import static org.jooq.impl.DSL.val;

@Configuration
public class TestParam implements CommandLineRunner {

    @Autowired
    private DSLContext ctx;

    @Override
    public void run(String... args) throws Exception {
        //testOne(5000, "Sales Rep");
        //testTwoOperatorVal(5000, "Sales Rep");
        //testThree();
        //testSqlNative(5000, "Sales Rep");
        //testExtractParam(5000, "Sales Rep");
    }

    public void testExtractParam(int salary, String job) {
        var result = ctx.select(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME)
                .from(Employee.EMPLOYEE)
                .where(Employee.EMPLOYEE.SALARY.gt(salary))
                .and(Employee.EMPLOYEE.JOB_TITLE.eq(job));

        System.out.println("bind values " + result.getBindValues());
    }

    public void testSqlNative(int salary, String job) {
        ctx.resultQuery("""
                Select first_name, last_name 
                 From employee where salary > ? and job_title = ?
                """, salary, job)
                .fetchInto(EmployeeName.class)
                .forEach(System.out::println);
    }

    public void testThree() {
        ctx.select(concat(Employee.EMPLOYEE.FIRST_NAME, val(" "), Employee.EMPLOYEE.LAST_NAME))
                .from(Employee.EMPLOYEE)
                .fetch()
                .stream()
                //.map(r -> r.get(0).toString() + r.get(1) + r.get(2))
                .forEach(System.out::println);
    }

    public void testTwoOperatorVal(int salary, String job) {
        ctx.select(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME)
                .from(Employee.EMPLOYEE)
                .where(val(salary).lt( Employee.EMPLOYEE.SALARY))
                //.and(Employee.EMPLOYEE.JOB_TITLE.eq(job))
                .fetchInto(EmployeeName.class)
                .stream()
                .forEach(System.out::println);
    }

    public void testOne(int salary, String job) {
        ctx.select(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME)
                .from(Employee.EMPLOYEE)
                .where(Employee.EMPLOYEE.SALARY.gt(salary))
                .and(Employee.EMPLOYEE.JOB_TITLE.eq(job))
                .fetchInto(EmployeeName.class)
                .stream()
                .forEach(System.out::println);
    }
}
