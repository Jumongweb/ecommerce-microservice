package com.jumonweb.product_service.service.interfaces;

import com.jumonweb.product_service.dtos.ProductRequest;
import com.jumonweb.product_service.dtos.ProductResponse;
import com.jumonweb.product_service.dtos.UpdateProductRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse getProduct(String productId);

    Map<String, ProductResponse> getAllProducts();

    ProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest);

    void deleteProduct(String productId);

}
