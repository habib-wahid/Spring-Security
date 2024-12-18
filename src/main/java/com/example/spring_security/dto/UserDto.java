package com.example.spring_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        @NotBlank
        @Size(min = 3, max = 10, message = "Username must be between 3-10 characters")
        String username,
        @NotBlank
        @Size(min = 4, max = 10, message = "Password must be between 4-10 characters")
        String password,
        @Email(message = "Must be valid email")
        String email,
        String firstName,
        String lastName) {
}
