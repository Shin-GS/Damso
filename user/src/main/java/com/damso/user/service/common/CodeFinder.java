package com.damso.user.service.common;

import com.damso.core.enums.CommonEnum;
import com.damso.user.service.common.model.CodeModel;

import java.util.List;

public interface CodeFinder {
    <T extends CommonEnum> List<CodeModel> getCodes(Class<T> enumClass);
}
