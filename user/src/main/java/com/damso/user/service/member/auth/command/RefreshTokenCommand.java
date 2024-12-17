package com.damso.user.service.member.auth.command;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(@NotBlank String refresh) {
}
