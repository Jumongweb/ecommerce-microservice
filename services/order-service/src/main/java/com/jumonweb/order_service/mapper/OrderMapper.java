package com.jumonweb.order_service.mapper;

import com.jumonweb.order_service.data.model.Order;
import com.jumonweb.order_service.dtos.OrderRequest;
import com.jumonweb.order_service.dtos.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    Order orderRequestToOrder(OrderRequest orderRequest);

    OrderResponse orderToOrderResponse(Order savedOrder);
}
