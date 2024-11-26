package com.example.backendeventmanagementbooking.enums;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum FilesExtensionTypes {
    PNG("png", MediaType.IMAGE_PNG),
    JPG("jpg", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    WEBP("webp", MediaType.valueOf("image/webp")),
    GIF("gif", MediaType.IMAGE_GIF),
    PDF("pdf", MediaType.APPLICATION_PDF),
    BMP("bmp", MediaType.valueOf("image/bmp")),
    BZIP("bzip", MediaType.valueOf("application/x-bzip")),
    MP3("mp3", MediaType.valueOf("audio/mpeg")),
    MP4("mp4", MediaType.valueOf("video/mp4"));

    private final String value;

    private final MediaType header;

    FilesExtensionTypes(String value, MediaType header) {
        this.value = value;
        this.header = header;
    }
}