package com.example.filent.model.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password,
    @NotBlank String role,  // "CLIENT" or "FREELANCER"
    @NotBlank String fullName
) {}