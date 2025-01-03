package com.damso.core.constant.story;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoryType implements EnumInfo {
    TEXT("텍스트"),
    IMAGE("이미지"),
    VIDEO("비디오");

    private final String description;
}
