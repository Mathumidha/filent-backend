package com.example.filent.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record GigRequest(
    @NotBlank String title,
    @NotBlank String description,
    @NotBlank String category,
    @Positive double price,
    @Positive int deliveryTime
) {}