package com.damso.user.service.member.auth.command;

import jakarta.validation.constraints.NotBlank;

public record SNSCommand(@NotBlank(message = "SNS 토큰을 입력해주세요")
                         String snsToken) {
}
