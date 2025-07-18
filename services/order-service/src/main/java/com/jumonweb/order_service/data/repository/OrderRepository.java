package com.jumonweb.order_service.data.repository;

import com.jumonweb.order_service.data.model.Order;
import com.jumonweb.order_service.dtos.OrderRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(UUID.randomUUID().toString());
        }
        order.setOrderDate(LocalDateTime.now());
        orders.put(order.getId(), order);
        return order;
    }

    public Map<String, Order> findAll() {
        return new HashMap<>(orders);
    }
}
