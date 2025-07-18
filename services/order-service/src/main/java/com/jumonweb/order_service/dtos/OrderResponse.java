package com.jumonweb.order_service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {
    private String id;
    private String productId;
    private int quantity;
    private LocalDateTime orderDate;
}
