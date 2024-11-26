package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = """
            SELECT u.*
            FROM user u
                     JOIN profile p ON p.uuid = u.profile_uuid
            WHERE u.username = :username
               OR u.email = :username
               OR p.identification = :username
            """, nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);


}

