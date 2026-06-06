package com.example.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {
}

// OLD CODE - kept for reference
// public class RegisterRequest {
// }
