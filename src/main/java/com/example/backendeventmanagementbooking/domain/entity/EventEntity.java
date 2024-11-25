package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.domain.dto.request.EventDto;
import com.example.backendeventmanagementbooking.domain.dto.request.EventUpdatedDto;
import com.example.backendeventmanagementbooking.enums.EventAccessType;
import com.example.backendeventmanagementbooking.enums.StatusEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import static com.example.backendeventmanagementbooking.enums.StatusEvent.ACTIVE;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventAccessType accessType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEvent status;

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
        this.status =ACTIVE;
        this.accessType = eventDto.getAccessType();
    }
    public EventEntity(EventUpdatedDto eventDto, UserEntity user) {
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
        this.status =ACTIVE;
    }

    public EventEntity fromEventUpdateDto(EventUpdatedDto eventUpdatedDto, UUID uuid) {
        var eventEntity = this;
        eventEntity.setTitle(eventUpdatedDto.getTitle());
        eventEntity.setDescription(eventUpdatedDto.getDescription());
        eventEntity.setPathImage(eventUpdatedDto.getPathImage());
        eventEntity.setStartDate(eventUpdatedDto.getStartDate());
        eventEntity.setEndDate(eventUpdatedDto.getEndDate());
        eventEntity.setLocation(eventUpdatedDto.getLocation());
        eventEntity.setCapacity(eventUpdatedDto.getCapacity());
        eventEntity.setPrice(eventUpdatedDto.getPrice());
        eventEntity.setTypeEvent(eventUpdatedDto.getTypeEvent());
        eventEntity.setStatus(StatusEvent.ACTIVE);
        eventEntity.setUuid(uuid);
        return eventEntity;
    }


}
