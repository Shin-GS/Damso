package com.damso.user.service.auth.command;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(@NotBlank String refresh) {
}
