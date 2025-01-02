package com.damso.auth.service.command;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(@NotBlank String refresh) {
}
