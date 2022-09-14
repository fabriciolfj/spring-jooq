package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.pojo.Order;
import com.github.fabriciolfj.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findOrderBetweenPeriod(final LocalDate start, final LocalDate end) {
        return orderRepository.findOrdersByRequiredDate(start, end);
    }
}
