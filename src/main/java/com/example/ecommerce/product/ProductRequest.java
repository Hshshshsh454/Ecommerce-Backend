package com.example.ecommerce.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Name is required")
        String name,

        @Positive(message = "Price must be positive")
        BigDecimal price,

        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock
) {
}