package com.github.fabriciolfj.study.config;

import com.github.fabriciolfj.study.pojo.CustomerDetail;
import com.zaxxer.hikari.util.SuspendResumeLock;
import jooq.generated.tables.Customer;
import jooq.generated.tables.Customerdetail;
import jooq.generated.tables.records.CustomerRecord;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class TestConsulta implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        //consultaSeguraSimples();
        joinTipoNaoSeguro();
        //joinTipoSeguro();
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
