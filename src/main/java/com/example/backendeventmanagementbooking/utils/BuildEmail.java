package com.example.backendeventmanagementbooking.utils;

import com.example.backendeventmanagementbooking.domain.dto.common.BuildEmailDto;
import com.example.backendeventmanagementbooking.enums.EmailFileNameTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildEmail {

    private final ResourceLoader resourceLoader;

    private final static String RESOURCES_PATH = "classpath:/templates/";

    public String getStringFromResource(String fileName) throws IOException {
        var resource = resourceLoader.getResource(RESOURCES_PATH + fileName);
        var fileData = Files.readAllBytes(Paths.get(resource.getURI()));
        return new String(fileData);
    }

    public String getTemplateEmail(EmailFileNameTemplate fileName, BuildEmailDto... buildEmailDtos) throws IOException {
        var bodyEmail = getStringFromResource(fileName.getValue());
        for (var buildEmailDto : buildEmailDtos) {
            bodyEmail = bodyEmail.replaceAll(buildEmailDto.keyWord(), buildEmailDto.value());
        }
        return bodyEmail;
    }
}


