package com.damso.admin.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberStatusType implements EnumInfo {
    ACTIVE("활성화"),
    BLOCKED("차단"),
    WITHDRAWN("탈퇴회원");

    private final String description;
}
