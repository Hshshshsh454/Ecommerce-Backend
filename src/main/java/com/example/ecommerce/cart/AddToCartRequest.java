package com.example.ecommerce.cart;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(

        @NotNull
        Long productId,

        @Positive
        Integer quantity
) {
}
