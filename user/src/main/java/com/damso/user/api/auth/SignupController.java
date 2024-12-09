package com.damso.user.api.auth;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.member.auth.MemberRegister;
import com.damso.user.service.member.auth.command.EmailDuplicationCommand;
import com.damso.user.service.member.auth.command.SignupCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/signup")
@RequiredArgsConstructor
public class SignupController {
    private final MemberRegister memberRegister;

    @PostMapping("/check-email")
    public SuccessResponse checkEmail(@Valid @RequestBody EmailDuplicationCommand command) {
        memberRegister.checkEmailDuplication(command.email());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PostMapping
    public SuccessResponse signup(@Valid @RequestBody SignupCommand command) {
        memberRegister.signup(command);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
