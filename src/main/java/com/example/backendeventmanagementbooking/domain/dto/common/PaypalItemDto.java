package com.example.backendeventmanagementbooking.domain.dto.common;

public record PaypalItemDto(String name,
    Integer quantity, String description, String sku, Double unitPrice) {   
}
