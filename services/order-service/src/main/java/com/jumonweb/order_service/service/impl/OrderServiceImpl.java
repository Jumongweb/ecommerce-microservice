package com.jumonweb.order_service.service.impl;

import com.jumonweb.order_service.data.model.Order;
import com.jumonweb.order_service.data.repository.OrderRepository;
import com.jumonweb.order_service.dtos.OrderRequest;
import com.jumonweb.order_service.dtos.OrderResponse;
import com.jumonweb.order_service.exception.OrderException;
import com.jumonweb.order_service.exception.ProductNotFoundException;
import com.jumonweb.order_service.mapper.OrderMapper;
import com.jumonweb.order_service.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        validateRequest(orderRequest);
        log.info("Creating order for productId: {}", orderRequest.getProductId());
        try {
            restTemplate.getForObject("http://product-service/api/v1/products/{id}", Object.class, orderRequest.getProductId());
        } catch (HttpClientErrorException ex) {
            log.warn("Product not found for id: {}", orderRequest.getProductId());
            throw new ProductNotFoundException("Product not found with id: " + orderRequest.getProductId());
        }
        Order order = orderMapper.orderRequestToOrder(orderRequest);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToOrderResponse(savedOrder);
    }

    private void validateRequest(OrderRequest orderRequest) {
        if (orderRequest == null) {
            throw new OrderException("Order request cannot be null");
        }
        if (orderRequest.getProductId() == null || orderRequest.getProductId().isEmpty()) {
            throw new OrderException("Product ID cannot be empty");
        }
        if (orderRequest.getQuantity() <= 0) {
            throw new OrderException("Quantity must be greater than zero");
        }
    }

    public Map<String, OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        Map<String, Order> orders = orderRepository.findAll();
        Map<String, OrderResponse> responseMap = new HashMap<>();
        orders.forEach((id, order) -> responseMap.put(id, orderMapper.orderToOrderResponse(order)));
        return responseMap;
    }
}
