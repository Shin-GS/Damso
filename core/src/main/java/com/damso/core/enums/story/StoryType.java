package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryType implements CommonEnum {
    TEXT("TEXT", "텍스트", "텍스트 에디터를 통해 글과 사진을 한번에 업로드 가능함"),
    IMAGE("IMAGE", "이미지", "이미지만 업로드 가능하며 간단한 이미지 편집 기능을 제공함"),
    VIDEO("VIDEO", "비디오", "비디오 1개만 업로드 가능함");

    private final String code;
    private final String value;
    private final String description;
}
