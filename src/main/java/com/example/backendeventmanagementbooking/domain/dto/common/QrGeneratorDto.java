package com.example.backendeventmanagementbooking.domain.dto.common;

import com.example.backendeventmanagementbooking.enums.QrUsage;

import java.time.LocalDate;
import java.util.UUID;

/// `RESERVED/12313-13123123/899893-977973-93179/qr.png`
///
/// `qrUsage/uuid(random)/uuid(reserve)/qr.png`
///
/// content is ignored for s3 path location
/// @param uuid    the uuid
/// @param qrUsage the usage of qr
/// @param endDate the endDate ttl for s3 file ### optional
/// @param content the content render
public record QrGeneratorDto(QrUsage qrUsage,
                             UUID uuid,
                             UUID reserve,
                             LocalDate endDate,
                             String content) {
}
