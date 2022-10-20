package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Orderdetail;
import jooq.generated.tables.Product;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestUpdateUsingFrom implements CommandLineRunner {

    @Autowired
    private DSLContext ctx;

    @Override
    public void run(String... args) throws Exception {
        ctx.update(Product.PRODUCT)
                .set(Product.PRODUCT.BUY_PRICE, Orderdetail.ORDERDETAIL.PRICE_EACH)
                .from(Orderdetail.ORDERDETAIL)
                .where(Product.PRODUCT.PRODUCT_ID.eq(Orderdetail.ORDERDETAIL.PRODUCT_ID))
                .execute();
    }
}
