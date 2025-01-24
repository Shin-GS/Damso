package com.damso.core.enums.member;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatusType implements CommonEnum {
    ACTIVE("ACTIVE", "활성화", "정상적으로 사이트의 기능 이용 가능"),
    BLOCKED("BLOCKED", "차단", "운영자가 회원의 기능을 잠금처리하여 사이트의 기능 이용 불가"),
    WITHDRAWN("WITHDRAWN", "탈퇴회원", "회원이 탈퇴하여 사이트의 기능 이용 불가");

    private final String code;
    private final String value;
    private final String description;
}
