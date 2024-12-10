package com.damso.user.api.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.client.auth.model.OAuth2Model;
import com.damso.user.service.member.auth.JwtTokenProvider;
import com.damso.user.service.member.auth.OAuth2ClientService;
import com.damso.user.service.member.auth.command.EmailLoginCommand;
import com.damso.user.service.member.auth.command.SNSCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ClientService oAuth2ClientService;

    @PostMapping("/email")
    public SuccessResponse loginWithEmail(@Valid @RequestBody EmailLoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(command.email(), command.password()));
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateToken(authentication));
    }

    @PostMapping("/sns/{provider}")
    public SuccessResponse loginWithSNS(@PathVariable MemberSocialAccountType provider,
                                        @RequestBody SNSCommand command) {
        OAuth2Model snsUser = oAuth2ClientService.getUser(provider, command.snsToken());

        // todo 작업 필요
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(snsUser.getEmail(), null));
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.generateToken(authentication));
    }
}
