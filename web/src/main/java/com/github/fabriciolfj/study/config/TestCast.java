package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Payment;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class TestCast implements CommandLineRunner {

    private final DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        var result = context.select(Payment.PAYMENT.INVOICE_AMOUNT.cast(String.class).as("invoice_amount")
                , Payment.PAYMENT.CACHING_DATE.cast(LocalDate.class).as("caching_date"))
                .from(Payment.PAYMENT)
                .where(Payment.PAYMENT.CUSTOMER_NUMBER.eq(103l))
                .fetch();

        result.forEach(s -> System.out.println(s.get(Payment.PAYMENT.INVOICE_AMOUNT) + " - " + s.get(Payment.PAYMENT.CACHING_DATE)));
    }
}
