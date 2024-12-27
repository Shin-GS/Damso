package com.damso.admin.api.auth;

import com.damso.admin.service.auth.command.LoginCommand;
import com.damso.auth.service.CustomAuthenticationManager;
import com.damso.auth.service.JwtTokenProvider;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
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

    @PostMapping
    public SuccessResponse login(@Valid @RequestBody LoginCommand command,
                                 HttpServletResponse response) {
        Long memberId = customAuthenticationManager.authenticateAdmin(command.email(), command.password());
        jwtTokenProvider.generateJwtCookie(response, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
