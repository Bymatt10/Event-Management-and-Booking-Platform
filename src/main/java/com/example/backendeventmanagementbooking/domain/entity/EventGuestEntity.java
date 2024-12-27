package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.enums.EventAccessType;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventAccessType accessType;

    private String verificationCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatusType invitationStatus = InvitationStatusType.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public EventGuestEntity(EventEntity event, UserEntity user, EventAccessType eventAccessType, String shortUuid, InvitationStatusType invitationStatusType) {
        this.event = event;
        this.user = user;
        this.accessType = eventAccessType;
        this.verificationCode = shortUuid;
        this.invitationStatus = invitationStatusType;
        this.createdAt = LocalDateTime.now();
    }
}