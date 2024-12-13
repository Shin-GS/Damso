package com.damso.user.security.token;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    boolean validate(String token);

    Authentication getAuthentication(String token);

    String generateAccessToken(Long memberId);
}
