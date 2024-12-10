package com.damso.user.service.member.auth.command;

import com.damso.core.constant.MemberSocialAccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SNSSignupCommand(@NotBlank(message = "SNS 제공자를 입력해주세요")
                               MemberSocialAccountType provider,

                               @NotBlank(message = "SNS 토큰을 입력해주세요")
                               String providerAccountId,

                               @NotBlank(message = "이메일을 입력해주세요")
                               @Email(message = "올바른 형식의 이메일을 입력해주세요")
                               String email,

                               String name) {
    public static SNSSignupCommand of(MemberSocialAccountType provider,
                                      String providerAccountId,
                                      String email,
                                      String name) {
        return new SNSSignupCommand(provider,
                providerAccountId,
                email,
                name);
    }
}
