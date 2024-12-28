package com.example.backendeventmanagementbooking.domain.entity;

import java.time.LocalDate;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import com.example.backendeventmanagementbooking.enums.CardBrandType;
import com.example.backendeventmanagementbooking.enums.CardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    @Column(nullable = false, unique = true)
    private String number;

    @NotBlankWithFieldName
    @Size(min = 3, max = 4, message = "The cvc number must be between 3 and 4 digits.")
    @Column(nullable = false, unique = true)
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

    @OneToOne(targetEntity = ProfileEntity.class)
    @JoinColumn(nullable = false, unique = true)
    private ProfileEntity profile;

    @Transient
    public String getMaskedNumber() {
        return number.replaceAll("\\b(\\d{4})\\d{8,}(\\d{4})\\b", "$1********$2");
    }
}