package com.damso.admin.api.auth;

import com.damso.auth.service.JwtTokenProvider;
import com.damso.auth.service.command.RefreshTokenCommand;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
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
