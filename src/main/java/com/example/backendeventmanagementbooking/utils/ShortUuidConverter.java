package com.example.backendeventmanagementbooking.utils;

import com.luigivismara.shortuuid.ShortUuid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ShortUuidConverter implements AttributeConverter<ShortUuid, String> {
    @Override
    public String convertToDatabaseColumn(ShortUuid attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public ShortUuid convertToEntityAttribute(String dbData) {
        return dbData != null ?  new ShortUuid(dbData) : null;
    }
}