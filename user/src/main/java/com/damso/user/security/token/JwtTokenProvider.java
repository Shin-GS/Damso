package com.damso.user.security.token;

import com.damso.core.constant.AuthTokenStatus;
import com.damso.user.service.member.auth.model.MemberAuthModel;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    AuthTokenStatus validate(String token);

    Authentication getAuthentication(String token);

    MemberAuthModel generateAccessToken(Long memberId);

    MemberAuthModel refreshToken(String refreshToken);
}
