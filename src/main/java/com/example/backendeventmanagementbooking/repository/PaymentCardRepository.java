package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.PaymentCardEntity;
import com.example.backendeventmanagementbooking.domain.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentCardRepository extends JpaRepository<PaymentCardEntity, Long> {
    PaymentCardEntity findByNumberAndProfile(String cardNumber, ProfileEntity profile);

    @Modifying
    @Transactional
    @Query("UPDATE payment_card pc SET pc.defaultCard = false WHERE pc.profile = :profile AND pc.defaultCard = true")
    void deactivateDefaultCardsByProfile(@Param("profile") ProfileEntity profile);

    List<PaymentCardEntity> findByProfile(ProfileEntity profile);

    PaymentCardEntity findTopByProfileOrderByIdDesc(ProfileEntity profile);

    PaymentCardEntity findByProfileAndDefaultCardIsTrue(ProfileEntity profile);
}
