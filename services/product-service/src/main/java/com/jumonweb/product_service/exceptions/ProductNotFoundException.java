package com.jumonweb.product_service.exceptions;

public class ProductNotFoundException extends ProductException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
