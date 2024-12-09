package com.damso.user.service.member.auth.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDuplicationCommand(@NotBlank(message = "이메일을 입력해주세요")
                                      @Email(message = "올바른 형식의 이메일을 입력해주세요") String email) {
}
