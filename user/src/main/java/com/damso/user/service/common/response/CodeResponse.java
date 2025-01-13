package com.damso.user.service.common.response;

import com.damso.core.enums.CommonEnum;

public record CodeResponse(String code,
                           String value) {
    public static CodeResponse of(CommonEnum commonEnum) {
        return new CodeResponse(commonEnum.getCode(), commonEnum.getValue());
    }
}
