package com.damso.userservice.common.impl;

import com.damso.core.enums.CommonEnum;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.common.response.CodeResponse;
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
