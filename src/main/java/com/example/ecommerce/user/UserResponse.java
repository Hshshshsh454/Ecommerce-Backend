package com.example.ecommerce.user;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}