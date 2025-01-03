package com.damso.core.constant.subscribe;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionStatusType implements EnumInfo {
    ACTIVE("구독"),
    CANCELLED("구독 취소"),
    EXPIRED("구독 만료");

    private final String description;
}