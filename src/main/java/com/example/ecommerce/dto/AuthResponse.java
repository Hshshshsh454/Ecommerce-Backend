package com.example.ecommerce.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
}

// OLD CODE - kept for reference
// public record AuthResponse(
//         String accessToken,
//         String token
// ) {
// }
