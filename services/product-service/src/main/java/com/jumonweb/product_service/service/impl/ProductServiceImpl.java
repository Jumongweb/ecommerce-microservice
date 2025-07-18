package com.jumonweb.product_service.service.impl;

import com.jumonweb.product_service.data.model.Product;
import com.jumonweb.product_service.data.repository.ProductRepository;
import com.jumonweb.product_service.dtos.ProductRequest;
import com.jumonweb.product_service.dtos.ProductResponse;
import com.jumonweb.product_service.dtos.UpdateProductRequest;
import com.jumonweb.product_service.exceptions.ProductException;
import com.jumonweb.product_service.exceptions.ProductNotFoundException;
import com.jumonweb.product_service.mapper.ProductMapper;
import com.jumonweb.product_service.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse addProduct(ProductRequest productRequest) {
        validateRequest(productRequest);
        log.info("Product request: {}", productRequest);
        Product product = productMapper.toProductRequestToProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductToProductResponse(savedProduct);
    }

    private void validateRequest(ProductRequest productRequest) {
        if (productRequest == null) {
            throw new ProductException("Product request cannot be null");
        }
        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new ProductException("Product name cannot be empty");
        }
        if (productRequest.getPrice() == null ) {
            throw new ProductException("Product price cannot be empty");
        }
    }

    public ProductResponse getProduct(String productId) {
        validateProductId(productId);
        log.info("Fetching product with id: {}", productId);
        Product product = productRepository.findById(productId);
        if (product == null) {
            log.info("Product not found: {}", productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
        return productMapper.toProductToProductResponse(product);
    }

    private void validateProductId(String productId){
        if (productId == null || productId.isEmpty()) {
            throw new ProductException("Product id cannot be empty");
        }
    }

    @Override
    public Map<String, ProductResponse> getAllProducts() {
        Map<String, Product> products = productRepository.findAll();
        log.info("Found {} products", products.size());
        Map<String, ProductResponse> responseMap = new HashMap<>();
        products.forEach((id, product) -> {
            responseMap.put(id, productMapper.toProductToProductResponse(product));
        });
        return responseMap;
    }

    @Override
    public ProductResponse updateProduct(String productId, UpdateProductRequest updateProductRequest) {
        validateProductId(productId);
        validateUpdateProductRequest(updateProductRequest);
        log.info("Updating product with id: {}", productId);
        Product foundProduct = productRepository.findById(productId);
        if (foundProduct == null) {
            log.info("Product not found for update: {}", productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

        if (updateProductRequest.getName() != null) {
            foundProduct.setName(updateProductRequest.getName());
        }
        if (updateProductRequest.getPrice() != null) {
            foundProduct.setPrice(updateProductRequest.getPrice());
        }
        Product savedProduct = productRepository.save(foundProduct);
        return productMapper.toProductToProductResponse(savedProduct);
    }

    private void validateUpdateProductRequest(UpdateProductRequest updateProductRequest){
        if (updateProductRequest == null) {
            throw new ProductException("Product request cannot be null");
        }
    }

    public void deleteProduct(String productId) {
        log.info("Deleting product with id: {}", productId);
        if (!productRepository.findAll().containsKey(productId)) {
            log.info("Product not found for deletion: {}", productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
        productRepository.delete(productId);
    }

}
