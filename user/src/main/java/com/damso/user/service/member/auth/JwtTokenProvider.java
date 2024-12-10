package com.damso.user.service.member.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    String extractToken(HttpServletRequest request);

    boolean validate(String token);

    Authentication getAuthentication(String token);

    String generateToken(Authentication authentication);
}
