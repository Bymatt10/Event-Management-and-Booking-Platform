package com.example.backendeventmanagementbooking.utils;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GetStringFromResource {

    private final ResourceLoader resourceLoader;

    @Value("resources/templates/register_email.html")
    private Resource resource;
}
