package com.damso.user.api.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.filter.CustomAuthenticationManager;
import com.damso.user.filter.JwtTokenProvider;
import com.damso.user.service.member.auth.command.EmailLoginCommand;
import com.damso.user.service.member.auth.command.SNSCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationManager customAuthenticationManager;

    @PostMapping("/email")
    public SuccessResponse loginWithEmail(@Valid @RequestBody EmailLoginCommand command) {
        Long memberId = customAuthenticationManager.authenticateByEmail(command.email(), command.password());
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateAccessToken(memberId));
    }

    @PostMapping("/sns/{provider}")
    public SuccessResponse loginWithSNS(@PathVariable MemberSocialAccountType provider,
                                        @RequestBody SNSCommand command) {
        Long memberId = customAuthenticationManager.authenticateBySns(provider, command.snsToken());
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateAccessToken(memberId));
    }
}
