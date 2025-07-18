package com.jumonweb.product_service.mapper;

import com.jumonweb.product_service.data.model.Product;
import com.jumonweb.product_service.dtos.ProductRequest;
import com.jumonweb.product_service.dtos.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProductRequestToProduct(ProductRequest productRequest);

    ProductResponse toProductToProductResponse(Product savedProduct);

}
