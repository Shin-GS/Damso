package com.damso.user.service.member.auth.command;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailSignupCommand(@NotBlank(message = "이름 입력해주세요")
                                 String name,

                                 @NotBlank(message = "이메일을 입력해주세요")
                                 @Email(message = "올바른 형식의 이메일을 입력해주세요")
                                 String email,

                                 @NotBlank(message = "비밀번호를 입력해주세요")
                                 String password,

                                 @AssertTrue(message = "이용약관에 동의해주세요.")
                                 boolean termsAgreed) {
}
