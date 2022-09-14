package com.github.fabriciolfj.study.repository;

import com.github.fabriciolfj.study.pojo.Order;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.jooq.generated.tables.Order.ORDER;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    public final DSLContext ctx;

    public List<Order> findOrdersByRequiredDate(final LocalDate start, final LocalDate end) {
        return ctx.selectFrom(ORDER)
                .where(ORDER.REQUIREDDATE.between(start, end))
                .fetchInto(Order.class);
    }

}
