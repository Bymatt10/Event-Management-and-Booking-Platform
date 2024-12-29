package com.example.backendeventmanagementbooking.domain.entity;

import java.time.LocalDate;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.enums.CardBrandType;
import com.example.backendeventmanagementbooking.enums.CardType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(indexes = {
        @Index(columnList = "number", name = "card_number_idx")
})
@Entity(name = "payment_card")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlankWithFieldName
    @Size(min = 13, max = 19, message = "The card number must be between 13 and 19 digits.")
    @Column(nullable = false)
    private String number;

    @NotBlankWithFieldName
    @Size(min = 3, max = 4, message = "The cvc number must be between 3 and 4 digits.")
    @Column(nullable = false)
    private String cvcNumber;

    @NotBlankWithFieldName
    @Future(message = "The expiration date must be a future date.")
    @Column(nullable = false)
    private LocalDate expireDate;

    @NotBlankWithFieldName
    @Size(max = 100, message = "The name cannot exceed 100 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlankWithFieldName
    @Size(max = 100, message = "The card holder cannot exceed 100 characters.")
    @Column(nullable = false)
    private String cardHolderName;

    @NotBlankWithFieldName
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @NotBlankWithFieldName
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardBrandType cardBrand;

    @Column(nullable = false)
    private Boolean defaultCard;

    @ManyToOne(targetEntity = ProfileEntity.class)
    @JoinColumn(nullable = false)
    private ProfileEntity profile;

    @Transient
    public String getMaskedNumber() {
        if (cardBrand == CardBrandType.AMERICAN_EXPRESS) {
            return number.replaceAll("^(\\d{4})\\d{7}(\\d{4})$", "$1*******$2");
        }
        return number.replaceAll("\\b(\\d{4})\\d{8,}(\\d{4})\\b", "$1********$2");
    }

    @Transient
    public String getLastsDigits() {
        return number.substring(number.length() - 4);
    }
}