package com.github.fabriciolfj.study.repository;

import com.github.fabriciolfj.study.pojo.Order;
import jooq.generated.tables.Customer;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static jooq.generated.tables.Order.ORDER;
import static org.jooq.impl.DSL.asterisk;


@Repository
@RequiredArgsConstructor
public class OrderOldRepository {

    public final DSLContext ctx;

    public List<Order> findOrdersByRequiredDate(final LocalDate start, final LocalDate end) {
        return ctx.selectFrom(ORDER)
                .where(ORDER.REQUIRED_DATE.between(start, end))
                .fetchInto(Order.class);
    }

    public List<jooq.generated.tables.pojos.JooqOrder> findCustomersAndOrders() {
        return ctx.select(Customer.CUSTOMER.CUSTOMER_NAME, ORDER.ORDER_DATE)
                .from(ORDER)
                .innerJoin(Customer.CUSTOMER)
                .using(Customer.CUSTOMER.CUSTOMER_NUMBER)
                .orderBy(ORDER.ORDER_DATE.desc())
                .fetchInto(jooq.generated.tables.pojos.JooqOrder.class);
    }

    public void listColumnsExcept() {
        var result = ctx.select(asterisk().except(ORDER.COMMENTS, ORDER.STATUS))
                .from(ORDER)
                .where(ORDER.ORDER_ID.eq(10101L)).fetch();

        result.forEach(s -> System.out.println(s.get(ORDER.CUSTOMER_NUMBER) + " " + s.get(ORDER.ORDER_DATE)));
    }

}
