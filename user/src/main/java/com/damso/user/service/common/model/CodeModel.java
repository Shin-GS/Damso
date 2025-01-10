package com.damso.user.service.common.model;

import com.damso.core.enums.CommonEnum;

public record CodeModel(String code,
                        String value) {
    public static CodeModel of(CommonEnum commonEnum) {
        return new CodeModel(commonEnum.getCode(), commonEnum.getValue());
    }
}
