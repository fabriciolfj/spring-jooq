package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.pojo.Order;
import com.github.fabriciolfj.study.repository.OrderOldRepository;
import jooq.generated.tables.daos.OrderRepository;
import jooq.generated.tables.pojos.JooqOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOldRepository orderOldRepository;

    public List<JooqOrder> findOrderBetweenPeriod(final LocalDate start, final LocalDate end) {
        return orderRepository.fetchRangeOfRequiredDate(start, end);
    }

    public List<jooq.generated.tables.pojos.JooqOrder> findCustomersAndOrders() {
        return orderOldRepository.findCustomersAndOrders();
    }
}
