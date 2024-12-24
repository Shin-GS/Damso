package com.damso.user.api.auth;

import com.damso.auth.service.JwtTokenProvider;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.auth.MemberRegister;
import com.damso.user.service.auth.command.EmailDuplicationCommand;
import com.damso.user.service.auth.command.EmailSignupCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/signup")
@RequiredArgsConstructor
public class SignupApi {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRegister memberRegister;

    @PostMapping("/email/check-email")
    public SuccessResponse checkEmail(@Valid @RequestBody EmailDuplicationCommand command) {
        memberRegister.checkEmailDuplication(command.email());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/email")
    public SuccessResponse signup(@Valid @RequestBody EmailSignupCommand command) {
        Long memberId = memberRegister.signup(command);
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateAccessToken(memberId));
    }
}
