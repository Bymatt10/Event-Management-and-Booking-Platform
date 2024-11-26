package com.example.backendeventmanagementbooking.domain.dto.common;

import com.example.backendeventmanagementbooking.enums.QrUsage;
import com.luigivismara.shortuuid.ShortUuid;

/// `RESERVED/12313-13123123/899893-977973-93179/qr.png`
///
/// `qrUsage/uuid(random)/uuid(reserve)/qr.png`
///
/// content is ignored for s3 path location
///
/// @param uuidRandom the uuid
/// @param qrUsage    the usage of qr
/// @param content    the content render
public record QrGeneratorDto(QrUsage qrUsage,
                             ShortUuid uuidRandom,
                             ShortUuid reserve,
                             String content) {
    public String generatePathLocation() {
        return this.qrUsage.name() + "/"
                + this.uuidRandom.toString() + "/"
                + this.reserve.toString() + "/"
                + qrUsage.name().toLowerCase() + ".png";
    }
}
