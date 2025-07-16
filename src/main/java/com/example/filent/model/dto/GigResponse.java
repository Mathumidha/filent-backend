package com.example.filent.model.dto;

import lombok.Builder;

@Builder
public record GigResponse(
    Long id,
    String title,
    String description,
    String category,
    double price,
    int deliveryTime,
    String freelancerEmail
) {}