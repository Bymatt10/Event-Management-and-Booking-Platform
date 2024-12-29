package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.domain.dto.response.PaymentMethodResponseDto;
import com.example.backendeventmanagementbooking.service.CreditCardService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/method")
public class PaymentMethodController {
    private final CreditCardService creditCardService;

    @GetMapping("/list")
    public ResponseEntity<GenericResponse<List<PaymentMethodResponseDto>>> listPaymentMethods() {
        return creditCardService.getAllPaymentMethods().GenerateResponse();
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<PaymentMethodResponseDto>> createPaymentMethod(
            @RequestBody BankCardDto bankCardDto) {
        return creditCardService.saveCreditCard(bankCardDto).GenerateResponse();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse<Object>> deleteCreditCard(Long id) {
        return creditCardService.deleteCreditCard(id).GenerateResponse();
    }

    @PutMapping("/change/default")
    public ResponseEntity<GenericResponse<PaymentMethodResponseDto>> changeDefaultPaymentMethod(Long id){
        return creditCardService.changeDefaultPaymentMethod(id).GenerateResponse();
    }
}
