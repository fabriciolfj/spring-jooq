package com.github.fabriciolfj.study.controller;

import com.github.fabriciolfj.study.pojo.CustomerAndOrder;
import com.github.fabriciolfj.study.pojo.Order;
import com.github.fabriciolfj.study.service.OrderService;
import jooq.generated.tables.pojos.JooqOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{start}/{end}")
    public List<JooqOrder> findOrdersPeriod(@PathVariable("start") final String start, @PathVariable("end") final String end) {
        return orderService.findOrderBetweenPeriod(LocalDate.parse(start), LocalDate.parse(end));
    }

    @GetMapping("/customers")
    public List<jooq.generated.tables.pojos.JooqOrder> findAllCustomersAndOrders() {
        return orderService.findCustomersAndOrders();
    }
}
