package com.example.backendeventmanagementbooking.domain.dto.response;

import com.example.backendeventmanagementbooking.enums.CardBrandType;

public record PaymentMethodResponseDto(Long id,
                                       String cardNumber,
                                       String name,
                                       CardBrandType cardBrand,
                                       Boolean defaultCard) {
}
