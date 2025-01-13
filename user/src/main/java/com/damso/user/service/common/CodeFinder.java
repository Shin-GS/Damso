package com.damso.user.service.common;

import com.damso.core.enums.CommonEnum;
import com.damso.user.service.common.response.CodeResponse;

import java.util.List;

public interface CodeFinder {
    <T extends CommonEnum> List<CodeResponse> getCodes(Class<T> enumClass);
}
