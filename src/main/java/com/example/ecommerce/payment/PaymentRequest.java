package com.example.ecommerce.payment;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record PaymentRequest(

        @NotNull(
                message = "Order id required")
        Long orderId,

        @NotNull(
                message = "Payment method required")
        String method
) {
}