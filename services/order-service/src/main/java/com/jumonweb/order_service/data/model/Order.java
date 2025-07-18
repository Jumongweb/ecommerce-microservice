package com.jumonweb.order_service.data.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Order {
    private String id;
    private String productId;
    private int quantity;
    private LocalDateTime orderDate;
}
