package com.jumonweb.order_service.service.interfaces;

import com.jumonweb.order_service.dtos.OrderRequest;
import com.jumonweb.order_service.dtos.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    Map<String, OrderResponse> getAllOrders();
}
