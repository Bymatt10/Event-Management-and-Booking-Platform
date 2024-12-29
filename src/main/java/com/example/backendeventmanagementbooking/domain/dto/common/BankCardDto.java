package com.example.backendeventmanagementbooking.domain.dto.common;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.domain.entity.PaymentCardEntity;
import com.example.backendeventmanagementbooking.domain.entity.ProfileEntity;
import com.example.backendeventmanagementbooking.enums.CardType;
import com.example.backendeventmanagementbooking.utils.CreditCardValidator;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.EXPIRED_CREDIT_CARD_DATE_FORMATTER;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankCardDto {
    @NotBlankWithFieldName
    private CardType cardType;

    @NotBlankWithFieldName
    private String expiry;

    @NotBlankWithFieldName
    private String lastDigits;

    @NotBlankWithFieldName
    private String cardHolderName;

    @NotBlankWithFieldName
    private String name;

    @NotBlankWithFieldName
    private String number;

    @NotBlankWithFieldName
    private String securityCode;

    public Card toPaypalCard(String countryCode) {
        var card = new Card();
        card.addressPortable(new AddressPortable().countryCode(countryCode));
        card.cardType(this.cardType.name().toLowerCase());
        card.expiry(this.expiry);
        card.lastDigits(this.lastDigits);
        card.name(this.cardHolderName);
        card.number(this.number);
        card.securityCode(this.securityCode);

        return card;
    }

    public PaymentCardEntity toPaymentCardEntity(ProfileEntity profileEntity) {
        return PaymentCardEntity.builder()
                .number(this.number)
                .cardType(this.cardType)
                .cardBrand(CreditCardValidator.GetCardType(this.number))
                .name(this.name)
                .cardHolderName(this.cardHolderName)
                .expireDate(LocalDate.parse(expiry, EXPIRED_CREDIT_CARD_DATE_FORMATTER))
                .defaultCard(false)
                .profile(profileEntity)
                .cvcNumber(this.securityCode)
                .build();
    }
}
