package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String pathImage;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String typeEvent;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false)
    private UserEntity user;

    public EventEntity(EventDto eventDto, UserEntity user) {
        this.title = eventDto.getTitle();
        this.description = eventDto.getDescription();
        this.pathImage = eventDto.getPathImage();
        this.startDate = eventDto.getStartDate();
        this.endDate = eventDto.getEndDate();
        this.location = eventDto.getLocation();
        this.capacity = eventDto.getCapacity();
        this.price = eventDto.getPrice();
        this.typeEvent = eventDto.getTypeEvent();
        this.user = user;
    }
}
