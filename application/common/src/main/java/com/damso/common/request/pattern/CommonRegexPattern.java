package com.damso.common.request.pattern;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public interface CommonRegexPattern {
    String getCode();

    String getPattern();

    String getFePattern();

    String getMessage();

    static <T extends CommonRegexPattern> Map<String, T> getMap(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(CommonRegexPattern::getCode, memberRegexPattern -> memberRegexPattern));
    }
}
