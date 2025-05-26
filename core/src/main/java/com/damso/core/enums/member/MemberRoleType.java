package com.damso.core.enums.member;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRoleType implements CommonEnum {
    GUEST("GUEST", "비회원", "아직 사이트에 로그인하지 않은 비회원"),
    USER("USER", "개인회원", "콘텐츠를 구독할 수 있는 권한을 가진 회원"),
    CREATOR("CREATOR", "크리에이터", "콘텐츠를 제공하거나 구독할 수 있는 권한을 가진 회원"),
    ADMIN("ADMIN", "운영자", "어드민 페이지에 접근할 수 있는 권한을 가진 회원");

    private final String code;
    private final String value;
    private final String description;
}
