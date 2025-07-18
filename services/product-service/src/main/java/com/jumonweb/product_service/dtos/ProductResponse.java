package com.jumonweb.product_service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    private String id;
    private String name;
    private BigDecimal price;
}
