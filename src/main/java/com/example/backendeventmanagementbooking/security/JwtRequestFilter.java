package com.example.backendeventmanagementbooking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        // Exclude certain paths from JWT validation
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/v1/user/register") || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;  // Skip JWT validation for these paths
        }

        final var authorizationHeader = request.getHeader("Authorization");

        var username = "";
        var jwt = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (!username.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            customUserDetailService.setAuthentication(userDetails, jwt, request);
        }
        chain.doFilter(request, response);
    }
}

