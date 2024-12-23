package com.damso.auth.service;

import com.damso.auth.service.model.MemberAuthModel;
import com.damso.core.constant.AuthTokenStatus;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    AuthTokenStatus validate(String token);

    Authentication getAuthentication(String token);

    MemberAuthModel generateAccessToken(Long memberId);

    MemberAuthModel refreshToken(String refreshToken);
}
