package com.example.backendeventmanagementbooking.utils;

import org.springframework.http.HttpStatus;

import com.example.backendeventmanagementbooking.enums.CardBrandType;
import com.example.backendeventmanagementbooking.exception.CustomException;

public class CreditCardValidator {
        public static boolean IsValidCardNumber(String cardNumber) {
        var sum = 0;
        var alternate = false;

        for (var i = cardNumber.length() - 1; i >= 0; i--) {
            var n = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    public static CardBrandType GetCardType(String cardNumber) {
        var brand = switch (cardNumber) {
            case String c when c.matches("^4[0-9]{12}(?:[0-9]{3})?$") -> CardBrandType.VISA;
            case String c when c.matches("^5[1-5][0-9]{14}$") -> CardBrandType.MASTERCARD;
            case String c when c.matches("^3[47][0-9]{13}$") -> CardBrandType.AMERICAN_EXPRESS;
            default -> throw new CustomException(HttpStatus.CONFLICT, "Credit card brand unknown.");
        };
        return brand;
    }

}
