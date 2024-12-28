package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;

public interface CreditCardService {
    GenericResponse<Object> saveCreditCard(BankCardDto dto);
}