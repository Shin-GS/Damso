package com.damso.core.enums;

import java.util.Arrays;
import java.util.List;

public interface CommonEnum {
    String getCode();

    String getValue();

    String getDescription();

    static <T extends CommonEnum> T byCode(Class<T> enumClass, String code) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }

    static <T extends CommonEnum> List<T> getEnums(Class<T> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }
}
