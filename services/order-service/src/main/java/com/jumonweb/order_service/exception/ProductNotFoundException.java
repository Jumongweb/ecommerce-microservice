package com.jumonweb.order_service.exception;

public class ProductNotFoundException extends OrderException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
