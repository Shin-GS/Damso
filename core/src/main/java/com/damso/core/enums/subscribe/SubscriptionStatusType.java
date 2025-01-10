package com.damso.core.enums.subscribe;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubscriptionStatusType implements CommonEnum {
    ACTIVE("ACTIVE", "구독", "구독중"),
    CANCELLED("CANCELLED", "구독 취소", "과거에 구독했다가 구독을 취소함"),
    EXPIRED("EXPIRED", "구독 만료", "과거에 구독했다가 구독이 만료됨");

    private final String code;
    private final String value;
    private final String description;
}