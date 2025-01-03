package com.damso.core.constant.auth;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthTokenStatus implements EnumInfo {
    NORMAL("토큰 정상"),
    EXPIRED("토큰 만료"),
    TAMPERED("토큰 위조"),
    EMPTY("토큰 없음");

    private final String description;
}
