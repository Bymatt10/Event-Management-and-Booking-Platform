package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.EventCategoryEntity;
import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategoryEntity, Long> {
    List<EventCategoryEntity> findByEvent(EventEntity event);
}
