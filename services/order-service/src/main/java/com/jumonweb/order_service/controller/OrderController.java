package com.jumonweb.order_service.controller;

import com.jumonweb.order_service.dtos.OrderRequest;
import com.jumonweb.order_service.dtos.OrderResponse;
import com.jumonweb.order_service.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Request received to create order for productId: {}", orderRequest.getProductId());
        return ResponseEntity.status(201).body(orderService.createOrder(orderRequest));
    }

    @GetMapping
    public ResponseEntity<Map<String, OrderResponse>> getAllOrders() {
        log.info("Request received to fetch all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
