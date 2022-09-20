package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Payment;
import org.jooq.*;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
public class TestDml implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        //delete();
        select();
    }

    //posso utilizar query nativas
    private void select() {
        var result = context.resultQuery("select customer_name from customer")
                .fetch()
                .into(String.class);

        result.stream().forEach(s -> System.out.println(s));
    }

    private void delete() {
        //final Query query = context.query("DELETE from payment where customer_number = 103");
        //or
        final Query query1 = context.deleteFrom(Payment.PAYMENT).where(Payment.PAYMENT.CUSTOMER_NUMBER.eq(103L));
        //numero de linhas afetadas
        int result = query1.execute();

        System.out.println(result);

    }
}
