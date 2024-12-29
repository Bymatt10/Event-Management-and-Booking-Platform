package com.example.backendeventmanagementbooking.domain.dto.common;

import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.domain.entity.PaymentCardEntity;
import com.luigivismara.shortuuid.ShortUuid;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.EXPIRED_CREDIT_CARD_DATE_FORMATTER;

public record PayPalOrderDto(BankCardDto card, PaypalItemDto item) {

    public PayPalOrderDto fromPaymentCardAndEvent(PaymentCardEntity paymentCard, EventEntity eventEntity) {
        var card = BankCardDto.builder()
                .cardType(paymentCard.getCardType())
                .expiry(paymentCard.getExpireDate().format(EXPIRED_CREDIT_CARD_DATE_FORMATTER))
                .lastDigits(paymentCard.getLastsDigits())
                .cardHolderName(paymentCard.getCardHolderName())
                .name(paymentCard.getName())
                .number(paymentCard.getNumber())
                .securityCode(paymentCard.getCvcNumber())
                .build();
        var item = new PaypalItemDto(
                eventEntity.getTitle(),
                1,
                eventEntity.getDescription(),
                ShortUuid.encode(eventEntity.getUuid()).toString(),
                eventEntity.getPrice()
        );

        return new PayPalOrderDto(card, item);
    }

}
