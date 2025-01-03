package com.damso.auth.service;

import com.damso.core.constant.auth.AuthTokenStatus;
import com.damso.core.constant.auth.AuthTokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    String extractCookie(HttpServletRequest request, AuthTokenType type);

    AuthTokenStatus validate(String token);

    Authentication getAuthentication(String token);

    void generateJwtCookie(HttpServletResponse response, Long memberId);

    void refreshJwtCookie(HttpServletResponse response, String refreshToken);

    void deleteJwtCookie(HttpServletResponse response);
}
