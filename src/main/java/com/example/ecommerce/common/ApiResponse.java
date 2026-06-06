package com.example.ecommerce.common;


import lombok.Builder;

@Builder
public record ApiResponse<T>(

        boolean success,

        String message,

        T data
) {
}