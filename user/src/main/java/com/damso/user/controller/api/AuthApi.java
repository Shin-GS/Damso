package com.damso.user.controller.api;

import com.damso.auth.service.CustomAuthenticationManager;
import com.damso.auth.service.JwtTokenProvider;
import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.auth.MemberRegister;
import com.damso.user.service.auth.RefreshInfoFetcher;
import com.damso.user.service.auth.command.EmailDuplicationCommand;
import com.damso.user.service.auth.command.EmailLoginCommand;
import com.damso.user.service.auth.command.EmailSignupCommand;
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
    public SuccessResponse loginWithEmail(@Valid @RequestBody EmailLoginCommand command,
                                          HttpServletResponse response) {
        Long memberId = customAuthenticationManager.authenticateNotAdmin(command.email(), command.password());
        jwtTokenProvider.generateJwtCookie(response, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/signup/email/check-email")
    public SuccessResponse checkEmail(@Valid @RequestBody EmailDuplicationCommand command) {
        memberRegister.checkEmailDuplication(command.email());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/signup/email")
    public SuccessResponse signup(@Valid @RequestBody EmailSignupCommand command,
                                  HttpServletResponse response) {
        Long memberId = memberRegister.signup(command);
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
