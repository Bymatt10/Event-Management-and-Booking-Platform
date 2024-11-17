package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Page<EventEntity> findAllByUser(Pageable pageable, UserEntity user);

    List<EventEntity> findAllEventsByUserUserId(UUID userId);
}
