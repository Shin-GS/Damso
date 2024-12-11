package com.damso.user.filter;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    boolean validate(String token);

    Authentication getAuthentication(String token);

    String generateAccessToken(Long memberId);
}
