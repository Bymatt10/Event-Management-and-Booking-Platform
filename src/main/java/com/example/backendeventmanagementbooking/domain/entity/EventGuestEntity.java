package com.example.backendeventmanagementbooking.domain.entity;

import com.example.backendeventmanagementbooking.enums.EventAccessType;
import com.example.backendeventmanagementbooking.enums.InvitationStatusType;
import com.example.backendeventmanagementbooking.utils.ShortUuidConverter;
import com.luigivismara.shortuuid.ShortUuid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "event_guest")
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

    @Convert(converter = ShortUuidConverter.class)
    private ShortUuid verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatusType invitationStatus = InvitationStatusType.PENDING;

}