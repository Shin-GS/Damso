package com.damso.admin.controller.auth;

import com.damso.admin.service.auth.RefreshInfoFetcher;
import com.damso.admin.service.auth.request.LoginRequest;
import com.damso.auth.service.CustomAuthenticationManager;
import com.damso.auth.service.JwtTokenProvider;
import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final RefreshInfoFetcher refreshInfoFetcher;

    @PostMapping("/login")
    public SuccessResponse login(@Valid @ModelAttribute LoginRequest request,
                                 HttpServletResponse response) {
        Long memberId = customAuthenticationManager.authenticateAdmin(request.email(), request.password());
        jwtTokenProvider.generateJwtCookie(response, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/logout")
    public SuccessResponse logout(HttpServletResponse response) {
        jwtTokenProvider.deleteJwtCookie(response);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/refresh-info")
    public SuccessResponse refreshInfo(@SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, refreshInfoFetcher.refresh(memberId));
    }
}
