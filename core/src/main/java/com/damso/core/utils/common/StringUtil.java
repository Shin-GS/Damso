package com.damso.core.utils.common;

import org.springframework.util.StringUtils;

public class StringUtil {
    private StringUtil() {
    }

    public static String defaultIfEmpty(String input, String defaultValue) {
        return StringUtils.hasText(input) ? input : defaultValue;
    }
}
