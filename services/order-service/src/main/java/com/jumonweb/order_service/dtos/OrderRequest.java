package com.jumonweb.order_service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderRequest {
    private String productId;
    private int quantity;
}
