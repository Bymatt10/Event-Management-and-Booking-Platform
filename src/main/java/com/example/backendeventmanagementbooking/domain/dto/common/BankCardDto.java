package com.example.backendeventmanagementbooking.domain.dto.common;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.enums.CardType;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.Card;

import lombok.Data;

@Data
public class BankCardDto {
    private AddressPortable addressPortable;

    @NotBlankWithFieldName
    private CardType cardType;

    @NotBlankWithFieldName
    private String expiry;

    @NotBlankWithFieldName
    private String lastDigits;

    @NotBlankWithFieldName
    private String name;

    @NotBlankWithFieldName
    private String number;

    @NotBlankWithFieldName
    private String securityCode;

    public Card toPaypalCard(){
        var card = new Card();
        this.addressPortable.countryCode("NI");
        card.addressPortable(this.addressPortable);
        card.cardType(this.cardType.name().toLowerCase());
        card.expiry(this.expiry);
        card.lastDigits(this.lastDigits);
        card.name(this.name);
        card.number(this.number);
        card.securityCode(this.securityCode);

        return card;
    }
}
