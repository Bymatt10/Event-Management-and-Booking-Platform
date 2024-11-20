package com.example.backendeventmanagementbooking.service.Impl;

import com.example.backendeventmanagementbooking.domain.dto.common.FileUploadDto;
import com.example.backendeventmanagementbooking.domain.dto.common.QrGeneratorDto;
import com.example.backendeventmanagementbooking.service.AwsService;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.example.backendeventmanagementbooking.utils.ImageUtils;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final S3Client s3Client;

    @Override
    public GenericResponse<Object> uploadFile(MultipartFile file, String route, String bucket) throws IOException {
        var routeSave = route + "/" + file.getOriginalFilename();
        final var putObject = PutObjectRequest.builder().bucket(bucket).key(routeSave).build();
        var fileUpload = FileUploadDto.builder()
                .fileName(file.getOriginalFilename())
                .bucket(bucket)
                .extension(Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename())))
                .path(routeSave)
                .build();
        var s3saved = s3Client.putObject(putObject, RequestBody.fromBytes(file.getBytes()));
        log.info("s3 location saved {}", s3saved);
        return new GenericResponse<>(HttpStatus.OK, fileUpload);
    }


    @Override
    public String generateDownloadFile(String bucket, String routePath) {
        final var getUrl = GetUrlRequest.builder().bucket(bucket).key(routePath).build();
        final var url = s3Client.utilities().getUrl(getUrl);
        return url.toString();
    }

    @Override
    public GenericResponse<Resource> httpDownload(String path, String bucketName) {
        final var x = s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(path).build());
        return new GenericResponse<>(HttpStatus.OK, new InputStreamResource(x));
    }

    @Override
    public GenericResponse<Object> uploadQrImage(BufferedImage file, QrGeneratorDto qrGeneratorDto, String bucket) throws IOException {
        final var putObject = PutObjectRequest.builder()
                .bucket(bucket)
                .key(qrGeneratorDto.generatePathLocation())
                .build();

        var fileUpload = FileUploadDto.builder()
                .fileName("qr.png")
                .bucket(bucket)
                .extension(".png")
                .path(qrGeneratorDto.generatePathLocation())
                .build();

        var s3saved = s3Client.putObject(putObject, RequestBody.fromBytes(ImageUtils.toByteArray(file, "png")));
        log.info("s3 location saved {}", s3saved);
        return new GenericResponse<>(HttpStatus.OK, fileUpload);
    }
}
