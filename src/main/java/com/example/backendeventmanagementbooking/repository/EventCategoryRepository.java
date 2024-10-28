package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.EventCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategoryEntity, Long> {
}
