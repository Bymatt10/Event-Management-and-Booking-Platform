package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.service.CreditCardService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/method")
public class PaymentMethodController {
    private final CreditCardService creditCardService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<Object>> createPaymentMethod(@RequestBody BankCardDto bankCardDto) {
        return creditCardService.saveCreditCard(bankCardDto).GenerateResponse();
    }
}