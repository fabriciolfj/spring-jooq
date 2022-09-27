package com.github.fabriciolfj.study.config;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import static jooq.generated.Tables.PRODUCT;

@Configuration
public class TestCollation implements CommandLineRunner {

    @Autowired
    private DSLContext ctx;

    @Override
    public void run(String... args) throws Exception {
        /*ctx.select(PRODUCT.PRODUCT_NAME)
                .from(PRODUCT)
                .orderBy(PRODUCT.PRODUCT_NAME.collate("de-CH-x-icu"))
                .fetch()
                .forEach(System.out::println);*/
    }
}
