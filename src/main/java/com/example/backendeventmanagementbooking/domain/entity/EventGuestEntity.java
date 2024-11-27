package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.enums.EventAccessType;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name = "event_guest")
@DynamicInsert
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventGuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = EventEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private EventEntity event;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventAccessType accessType;

    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatusType invitationStatus = InvitationStatusType.PENDING;

    public EventGuestEntity(EventEntity event, UserEntity user, EventAccessType eventAccessType, String shortUuid, InvitationStatusType invitationStatusType) {
        this.event = event;
        this.user = user;
        this.accessType = eventAccessType;
        this.verificationCode = shortUuid;
        this.invitationStatus = invitationStatusType;
    }
}