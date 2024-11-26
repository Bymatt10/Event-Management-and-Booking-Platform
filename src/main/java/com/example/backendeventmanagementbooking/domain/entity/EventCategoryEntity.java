package com.example.backendeventmanagementbooking.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "event_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = EventEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private EventEntity event;

    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private CategoryEntity category;
}
