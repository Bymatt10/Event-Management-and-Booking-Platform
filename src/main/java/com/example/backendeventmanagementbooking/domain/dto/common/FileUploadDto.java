package com.example.backendeventmanagementbooking.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadDto {
    private String fileName;
    private String path;
    private String bucket;
    private String extension;
}
