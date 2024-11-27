package com.demo.admin.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRoleType implements EnumInfo {
    USER("개인회원"),
    USER_MANAGER("매니저 - 개인회원 관리 가능"),
    NOTIFICATION_MANAGER("매니저 - 개인회원 알림 발송 가능"),
    ADMIN("운영자 - 개인회원 관리와 알림 발송 가능"),
    SUPER_ADMIN("슈퍼 어드민 - 운영자 권한 부여 가능");

    private final String description;
}
