package com.github.fabriciolfj.study.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static jooq.generated.Tables.PRODUCT;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.avg;

@Repository
@RequiredArgsConstructor
public class ProductV2Repository {

    private final DSLContext context;

    @Transactional
    public void insertProduct() {

        System.out.println("EXAMPLE 5 (affected rows): "
                + context.insertInto(PRODUCT,
                        PRODUCT.PRODUCT_ID, PRODUCT.PRODUCT_NAME, PRODUCT.PRODUCT_LINE,
                        PRODUCT.CODE, PRODUCT.PRODUCT_SCALE, PRODUCT.PRODUCT_DESCRIPTION,
                        PRODUCT.PRODUCT_VENDOR, PRODUCT.QUANTITY_IN_STOCK,
                        PRODUCT.BUY_PRICE, PRODUCT.MSRP)
                .values(field(select(max(PRODUCT.PRODUCT_ID.plus(1))).from(PRODUCT)),
                        val("1985s Green Bree Helicopter"), val("Planes"),
                        field(select(min(PRODUCT.CODE)).from(PRODUCT)
                                .where(PRODUCT.PRODUCT_LINE.eq("Planes"))),
                        val("1:10"), val("Red Start Diecast"), val("PENDING"), val(0),
                        field(select(avg(PRODUCT.BUY_PRICE)).from(PRODUCT)),
                        field(select(avg(PRODUCT.MSRP)).from(PRODUCT)))
                .execute()
        );
    }

}
