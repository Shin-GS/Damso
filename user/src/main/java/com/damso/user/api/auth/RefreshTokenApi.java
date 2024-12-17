package com.damso.user.api.auth;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.security.token.JwtTokenProvider;
import com.damso.user.service.member.auth.command.RefreshTokenCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/refresh")
@RequiredArgsConstructor
public class RefreshTokenApi {
    private final JwtTokenProvider jwtTokenProvider;

    @PutMapping
    public SuccessResponse refreshToken(@RequestBody RefreshTokenCommand command) {
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.refreshToken(command.refresh()));
    }
}
