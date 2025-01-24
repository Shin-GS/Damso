package com.damso.userservice.common;

import com.damso.core.enums.CommonEnum;
import com.damso.userservice.common.response.CodeResponse;

import java.util.List;

public interface CodeFinder {
    <T extends CommonEnum> List<CodeResponse> getCodes(Class<T> enumClass);
}
