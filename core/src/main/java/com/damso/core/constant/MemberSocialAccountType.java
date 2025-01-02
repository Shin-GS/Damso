package com.damso.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum MemberSocialAccountType implements EnumInfo {
    GOOGLE("GOOGLE"),
    X("X"),
    INSTAGRAM("INSTAGRAM"),
    NAVER("NAVER"),
    ;

    private final String description;

    public static MemberSocialAccountType findByName(String name) {
        return Arrays.stream(MemberSocialAccountType.values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
