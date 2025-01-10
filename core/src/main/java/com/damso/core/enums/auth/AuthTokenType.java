package com.damso.core.enums.auth;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthTokenType implements CommonEnum {
    AUTH("AUTH", "AUTH TOKEN", "로그인 상태유지를 위한 토큰"),
    REFRESH("REFRESH", "REFRESH TOKEN", "AUTH 토큰이 만료된 후 AUTH 토큰을 재발급 받기 위한 위한 토큰");

    private final String code;
    private final String value;
    private final String description;
}
