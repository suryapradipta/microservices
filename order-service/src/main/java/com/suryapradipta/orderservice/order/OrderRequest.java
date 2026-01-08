package com.suryapradipta.orderservice.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequest(
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
