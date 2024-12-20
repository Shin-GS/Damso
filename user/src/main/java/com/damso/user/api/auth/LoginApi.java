package com.damso.user.api.auth;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.security.CustomAuthenticationManager;
import com.damso.user.security.token.JwtTokenProvider;
import com.damso.user.service.auth.command.EmailLoginCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginApi {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationManager customAuthenticationManager;

    @PostMapping("/email")
    public SuccessResponse loginWithEmail(@Valid @RequestBody EmailLoginCommand command) {
        Long memberId = customAuthenticationManager.authenticate(command.email(), command.password());
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateAccessToken(memberId));
    }
}
