package com.github.fabriciolfj.study.config;

import com.github.fabriciolfj.study.pojo.CustomerDetail;
import com.github.fabriciolfj.study.pojo.OrderDetailSum;
import com.zaxxer.hikari.util.SuspendResumeLock;
import jooq.generated.tables.*;
import jooq.generated.tables.records.CustomerRecord;
import jooq.generated.tables.records.SaleRecord;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static jooq.generated.tables.Orderdetail.ORDERDETAIL;
import static org.jooq.impl.DSL.*;

@Configuration
public class TestConsulta implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Autowired
    private DataSource ds;

    @Override
    public void run(String... args) throws Exception {
        //usandoExpressoes();
        //consultaSeguraSimples();
        //joinTipoNaoSeguro();
        //joinTipoSeguro();
        //usandoDataSource();
        //usandoContextoDerivado();
        //mapeandoResultado();
        //union();
    }

    private void union() {
        var result =
                context.select(Employee.EMPLOYEE.FIRST_NAME, Employee.EMPLOYEE.LAST_NAME)
                .from(Employee.EMPLOYEE)
                .union(select(Customer.CUSTOMER.CONTACT_FIRST_NAME, Customer.CUSTOMER.CONTACT_LAST_NAME).from(Customer.CUSTOMER)
                        .join(Employee.EMPLOYEE)
                        .on(Customer.CUSTOMER.CUSTOMER_NUMBER.eq(Employee.EMPLOYEE.EMPLOYEE_NUMBER)))
                .fetch();

        result.forEach(s -> System.out.println(s.get(Employee.EMPLOYEE.FIRST_NAME) + " " + s.get(Employee.EMPLOYEE.LAST_NAME)));
    }

    private void mapeandoResultado() {
        context.selectFrom(Sale.SALE)
                .orderBy(Sale.SALE.SALE_ID)
                .fetch()
                .map(SaleRecord::getSale)
                .forEach(System.out::println);
    }

    private void usandoContextoDerivado() {
        var ctx = context.configuration().derive(new Settings().withRenderSchema(Boolean.FALSE)).dsl();
        var result =  ctx.select().from(Office.OFFICE)
                .where(Office.OFFICE.TERRITORY.eq("Japan")).fetch().into(com.github.fabriciolfj.study.pojo.Office.class);

        System.out.println(result);
    }

    private void usandoDataSource() {
        var result = DSL.using(ds, SQLDialect.POSTGRES)
                .selectFrom(Office.OFFICE)
                .where(Office.OFFICE.TERRITORY.eq("Japan"))
                .fetchInto(com.github.fabriciolfj.study.pojo.Office.class);

        System.out.println(result.get(0));
    }

    private void usandoExpressoes() {
        var result = context
                .select(ORDERDETAIL.ORDER_LINE_NUMBER,
                        sum(ORDERDETAIL.QUANTITY_ORDERED).as("itemsCount"),
                sum(ORDERDETAIL.QUANTITY_ORDERED.mul(ORDERDETAIL.PRICE_EACH)).as("total"))
                .from(ORDERDETAIL)
                        .where(val(20).lt(ORDERDETAIL.QUANTITY_ORDERED))
                .groupBy(ORDERDETAIL.ORDER_LINE_NUMBER)
                .orderBy(ORDERDETAIL.ORDER_LINE_NUMBER)
                .fetch()
                .into(OrderDetailSum.class);

        System.out.println(result.get(0));
    }

    //selectFrom e direto para a tabela e nao permite qualquer modificacao
    private void joinTipoSeguro() {
        final var result = context.selectFrom(Customer.CUSTOMER).fetch();

        for (Record r: result) {
            System.out.println(r.get(Customer.CUSTOMER.CUSTOMER_NAME));
        }
    }

    private void joinTipoNaoSeguro() {
        //final Result<Record3<String, String, BigDecimal>> result =
        final var result = context.select(Customer.CUSTOMER.CUSTOMER_NAME, Customerdetail.CUSTOMERDETAIL.CITY, Customer.CUSTOMER.CREDIT_LIMIT)
                .from(Customer.CUSTOMER)
                .join(Customerdetail.CUSTOMERDETAIL)
                .on(Customer.CUSTOMER.CUSTOMER_NUMBER.eq(Customerdetail.CUSTOMERDETAIL.CUSTOMER_NUMBER))
                .fetch()
                .into(CustomerDetail.class);

        //for (Record r: result) {
        for (CustomerDetail c : result) {
            /*System.out.println(r.get(Customer.CUSTOMER.CUSTOMER_NAME));
            System.out.println(r.get(Customerdetail.CUSTOMERDETAIL.CITY));
            System.out.println(r.get(Customer.CUSTOMER.CREDIT_LIMIT));*/

            System.out.println(c.getCustomerName() + "|" + c.getCity() + "|" + c.getCreditLimit());
        }
    }

    //seguro do tipo de dados das colunas
    private void consultaSeguraSimples() {
        var result = context.select().from(Customer.CUSTOMER)
                .fetch().into(CustomerRecord.class);

        for (CustomerRecord r: result) {
            System.out.println(r.getCustomerName());
            System.out.println(r.getCreditLimit());
        }
    }
}
