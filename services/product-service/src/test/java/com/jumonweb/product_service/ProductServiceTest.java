package com.jumonweb.product_service;

import com.jumonweb.product_service.data.repository.ProductRepository;
import com.jumonweb.product_service.dtos.ProductRequest;
import com.jumonweb.product_service.dtos.ProductResponse;
import com.jumonweb.product_service.dtos.UpdateProductRequest;
import com.jumonweb.product_service.exceptions.ProductException;
import com.jumonweb.product_service.exceptions.ProductNotFoundException;
import com.jumonweb.product_service.mapper.ProductMapper;
import com.jumonweb.product_service.service.impl.ProductServiceImpl;
import com.jumonweb.product_service.service.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ProductRequest productRequest;
    private ProductResponse productResponse;
    private String productId;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productMapper = Mappers.getMapper(ProductMapper.class);
        productService = new ProductServiceImpl(productRepository, productMapper);

        productRequest = ProductRequest.builder()
                .name("Test Product")
                .price(BigDecimal.TEN)
                .build();

        productResponse = productService.addProduct(productRequest);
        productId = productResponse.getId();
    }

    @Order(1)
    @Test
    void testCreateAndGetProduct() {
        productResponse = productService.addProduct(productRequest);
        assertNotNull(productResponse.getId());
        assertNotNull(productResponse.getName());
        assertNotNull(productResponse.getPrice());
    }

    @Order(2)
    @Test
    void testGetProduct() {
        log.info("Product Id : {}", productId);
        log.info("Product response : {}", productResponse.getId());
        productResponse = productService.getProduct(productId);
        assertEquals("Test Product", productResponse.getName());
        assertEquals(new BigDecimal("10"), productResponse.getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void testGetProductWithInvalidId(String invalidId) {
        assertThrows(ProductException.class, () -> productService.getProduct(invalidId));
    }

    @Test
    void testGetProductWithIdThatDoesNotExist() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct("nonexistentId"));
    }

    @Order(3)
    @Test
    void testGetAllProducts() {
        productService.addProduct(productRequest);
        Map<String, ProductResponse> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
        assertTrue(products.size() >= 1);
    }

    @Test
    void testGetProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct("nonexistent"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void testUpdateProductWithInvalidId(String invalidId) {
        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
                .name("Updated Test Product")
                .price(BigDecimal.valueOf(2000))
                .build();
        assertThrows(ProductException.class, () -> productService.updateProduct(invalidId, updateProductRequest));
    }

    @Test
    void testUpdateProductWithNullUpdateProductRequest() {
        assertThrows(ProductException.class, () -> productService.updateProduct(productId, null));
    }

    @Order(4)
    @Test
    void testUpdateProduct() {
        UpdateProductRequest updateProductRequest = UpdateProductRequest.builder()
                .name("Updated Test Product")
                .price(BigDecimal.valueOf(2000))
                .build();

        ProductResponse foundProduct = productService.getProduct(productId);
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
        assertThat(foundProduct.getPrice()).isEqualTo(BigDecimal.valueOf(10));

        ProductResponse updatedProduct = productService.updateProduct(productId, updateProductRequest);
        assertThat(updatedProduct.getName()).isEqualTo("Updated Test Product");
        assertEquals(new BigDecimal("2000"), updatedProduct.getPrice());

        ProductResponse persistedProduct = productService.getProduct(productId);
        assertThat(persistedProduct.getName()).isEqualTo("Updated Test Product");
        assertEquals(new BigDecimal("2000"), persistedProduct.getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void testDeleteProductWithInvalidId(String invalidId) {
        assertThrows(ProductException.class, () -> productService.deleteProduct(invalidId));
    }

    @Order(5)
    @Test
    void testDeleteProductSuccessfully() {
        ProductResponse existingProduct = productService.getProduct(productId);
        assertNotNull(existingProduct);

        productService.deleteProduct(productId);
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(productId));

        Map<String, ProductResponse> products = productService.getAllProducts();
        assertFalse(products.containsKey(productId));
    }


}