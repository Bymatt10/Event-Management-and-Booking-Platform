package com.example.backendeventmanagementbooking.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GeoLocationService {
    private final DatabaseReader databaseReader;

    public GeoLocationService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    /**
     * Retrieves the country code based on the client's IP address.
     *
     * @param request the HTTP request
     * @return the ISO country code, or "UNKNOWN" if unavailable
     */
    public String getCountryCode(HttpServletRequest request) {
        return Optional.ofNullable(getClientIp(request))
                .map(this::resolveCountryCode)
                .orElse("US");
    }

    /**
     * Resolves the country code using GeoIP.
     *
     * @param ip the client's IP address
     * @return the ISO country code, or "UNKNOWN" if an error occurs
     */
    private String resolveCountryCode(String ip) {
        try {
            var ipAddress = InetAddress.getByName(ip);
            return databaseReader.country(ipAddress).getCountry().getIsoCode();
        } catch (IOException | GeoIp2Exception e) {
            return "US";
        }
    }

    /**
     * Extracts the client's IP address from the request headers or remote address.
     *
     * @param request the HTTP request
     * @return the client's IP address, or null if unavailable
     */
    private String getClientIp(HttpServletRequest request) {
        var headers = new String[] {
                "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"
        };

        for (var header : headers) {
            var ip = request.getHeader(header);
            if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim(); // Take the first IP in the list
            }
        }
        return request.getRemoteAddr();
    }
}
