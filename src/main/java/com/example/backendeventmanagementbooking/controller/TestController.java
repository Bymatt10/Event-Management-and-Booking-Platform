package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.domain.dto.common.PayPalOrderDto;
import com.example.backendeventmanagementbooking.domain.dto.common.QrGeneratorDto;
import com.example.backendeventmanagementbooking.service.AwsService;
import com.example.backendeventmanagementbooking.service.PaypalOrder;
import com.example.backendeventmanagementbooking.utils.GenericResponse;
import com.example.backendeventmanagementbooking.utils.QrGenerator;
import com.google.common.io.Files;
import com.luigivismara.shortuuid.ShortUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import static com.example.backendeventmanagementbooking.enums.QrUsage.RESERVED;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@ConditionalOnExpression("${debug:true}")
@RestController
@RequestMapping("/api/v1/test/")
@RequiredArgsConstructor
public class TestController {

    private final QrGenerator qrGenerator;

    private final AwsService awsService;

    private final PaypalOrder paypalOrder;

    @Value("${s3.bucket.name.qr}")
    private String bucketName;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE, value = "{barcode}")
    public ResponseEntity<BufferedImage> getQR(@PathVariable("barcode") String barcode) throws IOException {
        var qr = qrGenerator.generateQR(barcode);
        awsService.uploadQrImage(qr, new QrGeneratorDto(
                RESERVED,
                ShortUuid.random(),
                ShortUuid.encode(UUID.randomUUID()),
                barcode
        ), bucketName);
        return new ResponseEntity<>(qr, HttpStatus.OK);
    }

    @PostMapping("test/")
    public ResponseEntity<GenericResponse<Object>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        var genericResponse = awsService.uploadFile(file, "test/" + UUID.randomUUID(), bucketName);
        return genericResponse.GenerateResponse();
    }

    @GetMapping("test/")
    public ResponseEntity<Object> downloadFile(@RequestParam String path) {
        var genericResponse = awsService.httpDownload(path, bucketName);

        var extension = Files.getFileExtension(path);

        var headers = new HttpHeaders();

        var ext = switch (extension) {
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "png", "jpeg", "gif" -> MediaType.IMAGE_PNG;
            default -> throw new IllegalStateException("Unexpected value: " + extension);
        };

        if (genericResponse.getStatus().equals(HttpStatus.OK)) {
            headers.setContentType(ext);
            headers.setContentDisposition(
                    ContentDisposition.builder("inline")
                            .filename(UUID.randomUUID() + "/preview-report." + extension).build()
            );
            return ResponseEntity.ok().headers(headers).body(genericResponse.getData());
        }

        return ResponseEntity.status(genericResponse.getStatus()).body(genericResponse.getData());
    }

    @PostMapping("paypal/payment")
    public ResponseEntity<Object> testPayPalPayment(@RequestBody String entity) throws IOException {
        var result = paypalOrder.executeOrder(new PayPalOrderDto("RapidFix", "entity", 200.0000));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
}
