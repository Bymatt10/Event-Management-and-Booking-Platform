package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "event_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public EventCategoryEntity(EventDto eventDto) {
        this.id = eventDto.getIdCategory();
    }
}
