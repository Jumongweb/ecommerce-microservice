package com.jumonweb.product_service.data.repository;

import com.jumonweb.product_service.data.model.Product;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        products.put(product.getId(), product);
        return product;
    }

    public Product findById(String productId) {
        return products.get(productId);
    }

    public Map<String, Product> findAll() {
        return new HashMap<>(products);
    }

    public void delete(String productId) {
        products.remove(productId);
    }

}
