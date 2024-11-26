package com.example.backendeventmanagementbooking.repository;

import com.example.backendeventmanagementbooking.domain.entity.EventEntity;
import com.example.backendeventmanagementbooking.domain.entity.UserEntity;
import com.example.backendeventmanagementbooking.enums.EventAccessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Page<EventEntity> findAllByUser(Pageable pageable, UserEntity user);

    List<EventEntity> findAllEventsByUserUserId(UUID userId);

    @Query(value = """
            SELECT * FROM event e WHERE e.access_type = 'PUBLIC' AND start_date >= NOW()
           """, nativeQuery = true)
    Page<EventEntity> findAllPublicEvents(Pageable pageable);

    @Query(value = "SELECT * FROM event e WHERE e.uuid = ? AND e.access_type = ? AND start_date >= NOW()", nativeQuery = true)
    EventEntity findEventByUuidAndAccessType(UUID eventId, EventAccessType eventAccessType);
}
