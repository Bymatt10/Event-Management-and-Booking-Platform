package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.common.BankCardDto;
import com.example.backendeventmanagementbooking.domain.dto.response.PaymentMethodResponseDto;
import com.example.backendeventmanagementbooking.exception.CustomException;
import com.example.backendeventmanagementbooking.repository.PaymentCardRepository;
import com.example.backendeventmanagementbooking.security.SecurityTools;
import com.example.backendeventmanagementbooking.service.CreditCardService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.backendeventmanagementbooking.utils.CreditCardValidator.IsValidCardNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final SecurityTools securityTools;
    private final PaymentCardRepository repository;

    @Override
    public GenericResponse<PaymentMethodResponseDto> saveCreditCard(BankCardDto dto) {
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

        return new GenericResponse<>(HttpStatus.CREATED,
                new PaymentMethodResponseDto(result.getId(),
                        result.getMaskedNumber(),
                        result.getName(),
                        result.getCardBrand(),
                        result.getDefaultCard()
                )
        );
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

            var cardDefault = repository.findTopByProfileOrderByIdDesc(profile);
            cardDefault.setDefaultCard(true);
            repository.save(cardDefault);
            return new GenericResponse<>(HttpStatus.OK);
        }
        return new GenericResponse<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public GenericResponse<List<PaymentMethodResponseDto>> getAllPaymentMethods() {
        var profile = securityTools.getCurrentUser().getProfile();

        var findAllByProfile = repository.findByProfile(profile)
                .stream()
                .parallel()
                .map(card -> new PaymentMethodResponseDto(card.getId(),
                        card.getMaskedNumber(),
                        card.getName(),
                        card.getCardBrand(),
                        card.getDefaultCard()
                )).toList();

        return new GenericResponse<>(HttpStatus.OK, findAllByProfile);
    }

    @Override
    public GenericResponse<PaymentMethodResponseDto> changeDefaultPaymentMethod(Long id) {
        var profile = securityTools.getCurrentUser().getProfile();
        var card = repository.findById(id);
        if (card.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
        if (!card.get().getProfile().equals(profile)) {
            return new GenericResponse<>(HttpStatus.NOT_FOUND);
        }
        if (!card.get().getDefaultCard()) {
            repository.deactivateDefaultCardsByProfile(profile);
            card.get().setDefaultCard(true);
            var result = repository.save(card.get());
            return new GenericResponse<>(HttpStatus.OK,
                    new PaymentMethodResponseDto(result.getId(),
                            result.getMaskedNumber(),
                            result.getName(),
                            result.getCardBrand(),
                            result.getDefaultCard()
                    )
            );
        }

        return new GenericResponse<>(HttpStatus.OK,
                new PaymentMethodResponseDto(card.get().getId(),
                        card.get().getMaskedNumber(),
                        card.get().getName(),
                        card.get().getCardBrand(),
                        card.get().getDefaultCard()
                )
        );
    }
}
