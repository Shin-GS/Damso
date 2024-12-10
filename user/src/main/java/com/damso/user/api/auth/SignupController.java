package com.damso.user.api.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.client.auth.model.OAuth2Model;
import com.damso.user.service.member.auth.MemberRegister;
import com.damso.user.service.member.auth.OAuth2ClientService;
import com.damso.user.service.member.auth.command.EmailDuplicationCommand;
import com.damso.user.service.member.auth.command.EmailSignupCommand;
import com.damso.user.service.member.auth.command.SNSCommand;
import com.damso.user.service.member.auth.command.SNSSignupCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class SignupController {
    private final MemberRegister memberRegister;
    private final OAuth2ClientService oAuth2ClientService;

    @PostMapping("/email/check-email")
    public SuccessResponse checkEmail(@Valid @RequestBody EmailDuplicationCommand command) {
        memberRegister.checkEmailDuplication(command.email());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/email")
    public SuccessResponse signupByEmail(@Valid @RequestBody EmailSignupCommand command) {
        memberRegister.signup(command);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping("/sns/{provider}")
    public SuccessResponse signupBySns(@PathVariable("provider") MemberSocialAccountType provider,
                                       @Valid @RequestBody SNSCommand command) {
        OAuth2Model snsUser = oAuth2ClientService.getUser(provider, command.snsToken());
        SNSSignupCommand snsSignupCommand = SNSSignupCommand.of(
                provider,
                snsUser.getProviderAccountId(),
                snsUser.getEmail(),
                snsUser.getName());
        memberRegister.signup(snsSignupCommand);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
