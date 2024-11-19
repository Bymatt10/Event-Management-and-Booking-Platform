package com.example.backendeventmanagementbooking.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Slf4j
@Component
public class QrGenerator {

    public BufferedImage generateQR(String text){
        try {
            var barcodeWriter = new QRCodeWriter();
            var bitMatrix =  barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
