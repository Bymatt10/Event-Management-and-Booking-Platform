package com.example.backendeventmanagementbooking.controller;

import com.example.backendeventmanagementbooking.utils.QrGenerator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api/v1/qr/")
@RequiredArgsConstructor
public class TestController {

    private final QrGenerator qrGenerator;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE, value = "{barcode}")
    public ResponseEntity<BufferedImage> getQR(@PathVariable("barcode") String barcode){
        var qr = qrGenerator.generateQR(barcode);
        return new ResponseEntity<>(qr, HttpStatus.OK);
    }
}
