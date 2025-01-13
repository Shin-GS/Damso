package com.damso.user.service.common.impl;

import com.damso.core.enums.CommonEnum;
import com.damso.user.service.common.CodeFinder;
import com.damso.user.service.common.response.CodeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeFinderImpl implements CodeFinder {
    @Override
    public <T extends CommonEnum> List<CodeResponse> getCodes(Class<T> enumClass) {
        return CommonEnum.getEnums(enumClass).stream()
                .map(CodeResponse::of)
                .toList();
    }
}
