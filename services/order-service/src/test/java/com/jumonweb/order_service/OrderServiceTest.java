package com.jumonweb.order_service;

import com.jumonweb.order_service.data.model.Order;
import com.jumonweb.order_service.data.repository.OrderRepository;
import com.jumonweb.order_service.dtos.OrderRequest;
import com.jumonweb.order_service.dtos.OrderResponse;
import com.jumonweb.order_service.exception.OrderException;
import com.jumonweb.order_service.mapper.OrderMapper;
import com.jumonweb.order_service.service.impl.OrderServiceImpl;
import com.jumonweb.order_service.service.interfaces.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = Mappers.getMapper(OrderMapper.class);
        restTemplate = mock(RestTemplate.class);
        orderService = new OrderServiceImpl(orderRepository, orderMapper, restTemplate);
    }

    @Test
    void testCreateOrderSuccess() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("1");
        orderRequest.setQuantity(2);

        Order mockOrder = new Order();
        mockOrder.setId("order123");
        mockOrder.setProductId("1");
        mockOrder.setQuantity(2);
        mockOrder.setOrderDate(LocalDateTime.now());

        when(restTemplate.getForObject("http://product-service/api/v1/products/{id}",
                Object.class, "1")).thenReturn(new Object());

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        OrderResponse response = orderService.createOrder(orderRequest);
        assertNotNull(response.getId());
        assertEquals("1", response.getProductId());
        assertEquals(2, response.getQuantity());
        assertNotNull(response.getOrderDate());
    }

    @Test
    void testCreateOrderProductNotFound() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("nonexistent");
        orderRequest.setQuantity(2);

        when(restTemplate.getForObject("http://product-service/api/v1/products/{id}",
                Object.class, "nonexistent"))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void testCreateOrderWithEmptyName() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("");
        orderRequest.setQuantity(1);
        assertThrows(OrderException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void testCreateOrderWithZeroQuantity() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("Gun");
        orderRequest.setQuantity(0);
        assertThrows(OrderException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    void testGetAllOrders() {
        Order mockOrder = new Order();
        mockOrder.setId("order123");
        mockOrder.setProductId("1");
        mockOrder.setQuantity(2);
        mockOrder.setOrderDate(LocalDateTime.now());

        Map<String, Order> ordersMap = new HashMap<>();
        ordersMap.put("order123", mockOrder);

        when(orderRepository.findAll()).thenReturn(ordersMap);

        Map<String, OrderResponse> result = orderService.getAllOrders();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.containsKey("order123"));
    }
}