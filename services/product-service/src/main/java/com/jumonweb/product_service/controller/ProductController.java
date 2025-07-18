package com.jumonweb.product_service.controller;

import com.jumonweb.product_service.dtos.ProductRequest;
import com.jumonweb.product_service.dtos.ProductResponse;
import com.jumonweb.product_service.dtos.UpdateProductRequest;
import com.jumonweb.product_service.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        log.info("Request received to create product on instance: {}", productRequest);
        return ResponseEntity.status(201).body(productService.addProduct(productRequest));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId) {
        log.info("Request received to fetch product with id: {}", productId);
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping
    public ResponseEntity<Map<String, ProductResponse>> getAllProducts() {
        log.info("Request received to fetch all products on instance: {}", System.getProperty("server.port", "unknown"));
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String productId, @RequestBody UpdateProductRequest updateProductRequest) {
        log.info("Request received to update product with id: {}", productId);
        return ResponseEntity.ok(productService.updateProduct(productId, updateProductRequest));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        log.info("Request received to delete product with id: {} ", productId);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
