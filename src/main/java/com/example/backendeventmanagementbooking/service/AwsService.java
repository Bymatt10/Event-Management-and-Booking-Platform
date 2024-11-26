package com.example.backendeventmanagementbooking.service;

import com.example.backendeventmanagementbooking.domain.dto.common.QrGeneratorDto;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface AwsService {
    GenericResponse<Object> uploadFile(MultipartFile file, String route, String bucket) throws IOException;

    GenericResponse<Object> uploadQrImage(BufferedImage file, QrGeneratorDto qrGeneratorDto, String bucket) throws IOException;

    String generateDownloadFile(String bucket, String filePath);

    GenericResponse<Resource> httpDownload(String path, String bucketName);
}
