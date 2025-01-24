package com.damso.core.enums.auth;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthTokenStatus implements CommonEnum {
    NORMAL("NORMAL", "NORMAL TOKEN", "정상적인 토큰"),
    EXPIRED("EXPIRED", "EXPIRED TOKEN", "인증이 만료된 토큰"),
    TAMPERED("TAMPERED", "TAMPERED TOKEN", "임의로 위조된 토큰"),
    EMPTY("EMPTY", "EMPTY TOKEN", "토큰이 없음");

    private final String code;
    private final String value;
    private final String description;
}
