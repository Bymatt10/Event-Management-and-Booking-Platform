package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM user WHERE username = :username OR email = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
