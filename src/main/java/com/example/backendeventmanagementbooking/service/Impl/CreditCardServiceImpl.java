package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.domain.entity.CardEntiy;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.PaymentCardRepository;
import com.example.backendeventmanagementbooking.security.SecurityTools;
import com.example.backendeventmanagementbooking.service.CreditCardService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.backendeventmanagementbooking.utils.CreditCardValidator.IsValidCardNumber;

@Log
@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

  private final SecurityTools securityTools;
  private final PaymentCardRepository repository;
  private final PaymentCardRepository paymentCardRepository;

  @Override
  public GenericResponse<Object> saveCreditCard(BankCardDto dto) {
    var user = securityTools.getCurrentUser();
    if (!IsValidCardNumber(dto.getNumber())) {
      throw new CustomException(HttpStatus.CONFLICT, "Credit card number isn't valid");
    }

    var paymentCardEntity = dto.toPaymentCardEntity(user.getProfile());

    if (repository.findByNumberAndProfile(dto.getNumber(), user.getProfile()) != null) {
      throw new CustomException(HttpStatus.CONFLICT, "Credit card number is already in use");
    }

    repository.deactivateDefaultCardsByProfile(user.getProfile());

    paymentCardEntity.setDefaultCard(true);
    var result = repository.save(paymentCardEntity);

    return new GenericResponse<>(HttpStatus.CREATED, result);
  }

  @Override
  public GenericResponse<Object> deleteCreditCard(Long id) {
    var profile = securityTools.getCurrentUser().getProfile();
    var card = repository.findById(id);
    if (card.isEmpty()) {
      throw new CustomException(HttpStatus.NOT_FOUND);
    }
    if (card.get().getProfile().equals(profile)) {
      repository.deleteById(id);
      return new GenericResponse<>(HttpStatus.ACCEPTED);
    }
    return new GenericResponse<>(HttpStatus.NOT_FOUND);
  }
}
