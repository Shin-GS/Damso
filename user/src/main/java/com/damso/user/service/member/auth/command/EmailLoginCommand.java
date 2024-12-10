package com.damso.user.service.member.auth.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailLoginCommand(@NotBlank(message = "이메일을 입력해주세요")
                                @Email(message = "올바른 형식의 이메일을 입력해주세요")
                                String email,

                                @NotBlank(message = "비밀번호를 입력해주세요")
                                String password) {
}
