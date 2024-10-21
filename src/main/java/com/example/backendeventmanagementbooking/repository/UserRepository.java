package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(UUID userId);
}

