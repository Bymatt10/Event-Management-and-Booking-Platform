package com.example.backendeventmanagementbooking.config;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maxmind.geoip2.DatabaseReader;

@Configuration
public class GeoLocationConfig {

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        var databasePath = "src/main/resources/ip/GeoLite2-Country.mmdb";
        var databaseFile = new File(databasePath);
        return new DatabaseReader.Builder(databaseFile).build();
    }
}
