package com.jumonweb.product_service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class UpdateProductRequest {
    private String name;
    private BigDecimal price;
}
