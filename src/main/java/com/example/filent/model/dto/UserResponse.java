package com.example.filent.model.dto;

public record UserResponse(
    Long id,
    String email,
    String role,
    String fullName
) {}