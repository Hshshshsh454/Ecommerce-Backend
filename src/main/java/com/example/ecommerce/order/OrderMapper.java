package com.example.ecommerce.order;

import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount()
        );
    }
}
