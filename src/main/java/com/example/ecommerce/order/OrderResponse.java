package com.example.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String status,
        BigDecimal totalAmount
) {
}
