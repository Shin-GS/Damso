package com.damso.user.controller.auth;

import com.damso.common.auth.CustomAuthenticationManager;
import com.damso.common.auth.JwtTokenProvider;
import com.damso.common.auth.session.SessionMemberId;
import com.damso.core.code.SuccessCode;
import com.damso.common.response.SuccessResponse;
import com.damso.userservice.auth.RefreshInfoFetcher;
import com.damso.userservice.auth.request.EmailDuplicationRequest;
import com.damso.userservice.auth.request.EmailLoginRequest;
import com.damso.userservice.auth.request.EmailSignupRequest;
import com.damso.userservice.member.MemberRegister;
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
    private final MemberRegister memberRegister;
    private final RefreshInfoFetcher refreshInfoFetcher;

    @PostMapping("/login/email")
    public SuccessResponse loginWithEmail(@Valid @ModelAttribute EmailLoginRequest request,
                                          HttpServletResponse response) {
        Long memberId = customAuthenticationManager.authenticateNotAdmin(request.email(), request.password());
        jwtTokenProvider.generateJwtCookie(response, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/signup/email/check-email")
    public SuccessResponse checkEmail(@Valid @RequestBody EmailDuplicationRequest request) {
        memberRegister.checkEmailDuplication(request.email());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/signup/email")
    public SuccessResponse signup(@Valid @ModelAttribute EmailSignupRequest request,
                                  HttpServletResponse response) {
        Long memberId = memberRegister.signup(request);
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
