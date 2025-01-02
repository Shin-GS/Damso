package com.damso.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthTokenType implements EnumInfo {
    AUTH("auth 토큰"),
    REFRESH("refresh 토큰");

    private final String description;
}
