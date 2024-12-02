package com.damso.admin.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationType implements EnumInfo {
    EMAIL("이메일"),
    SMS("SMS"),
    PUSH("PUSH"),
    TELEGRAM("TELEGRAM"),
    SLACK("SLACK");

    private final String description;
}
