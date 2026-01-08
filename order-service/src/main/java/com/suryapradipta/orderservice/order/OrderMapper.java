package com.suryapradipta.orderservice.order;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())))
                .quantity(orderRequest.quantity())
                .build();
    }
}
