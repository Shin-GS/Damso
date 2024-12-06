package com.damso.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberSocialAccountType implements EnumInfo {
    EMAIL("EMAIL"),
    GOOGLE("GOOGLE"),
    X("X"),
    INSTAGRAM("INSTAGRAM"),
    NAVER("NAVER"),
    ;

    private final String description;
}

