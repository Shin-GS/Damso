package com.damso.core.enums.member;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberSocialAccountType implements CommonEnum {
    GOOGLE("GOOGLE", "GOOGLE", "GOOGLE"),
    X("X", "X", "X"),
    INSTAGRAM("INSTAGRAM", "INSTAGRAM", "INSTAGRAM"),
    NAVER("NAVER", "NAVER", "NAVER");

    private final String code;
    private final String value;
    private final String description;
}
