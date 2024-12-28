package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendeventmanagementbooking.domain.entity.PaymentCardEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PaymentCardRepository extends JpaRepository<PaymentCardEntity, Long> {
    PaymentCardEntity findByNumberAndProfile(String cardNumber, ProfileEntity profile);

    @Modifying
    @Transactional
    @Query("UPDATE payment_card pc SET pc.defaultCard = false WHERE pc.profile = :profile AND pc.defaultCard = true")
    void deactivateDefaultCardsByProfile(@Param("profile") ProfileEntity profile);
}
