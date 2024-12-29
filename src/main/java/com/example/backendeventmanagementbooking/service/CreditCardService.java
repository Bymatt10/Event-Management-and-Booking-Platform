package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.domain.dto.response.PaymentMethodResponseDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

import java.util.List;

public interface CreditCardService {
    GenericResponse<PaymentMethodResponseDto> saveCreditCard(BankCardDto dto);

    GenericResponse<Object> deleteCreditCard(Long id);

    GenericResponse<List<PaymentMethodResponseDto>> getAllPaymentMethods();

    GenericResponse<PaymentMethodResponseDto> changeDefaultPaymentMethod(Long id);
}