package com.damso.user.api.auth;

import com.damso.auth.service.CustomAuthenticationManager;
import com.damso.auth.service.JwtTokenProvider;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.auth.command.EmailLoginCommand;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class LoginApi {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationManager customAuthenticationManager;

    @PostMapping("/email")
    public SuccessResponse loginWithEmail(@Valid @RequestBody EmailLoginCommand command,
                                          HttpServletResponse response) {
        Long memberId = customAuthenticationManager.authenticateNotAdmin(command.email(), command.password());
        jwtTokenProvider.generateJwtCookie(response, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
