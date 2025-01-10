package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryFileType implements CommonEnum {
    IMAGE("IMAGE", "이미지", "이미지 확장자를 가지는 파일"),
    VIDEO("VIDEO", "비디오", "비디오 확장자를 가지는 파일");

    private final String code;
    private final String value;
    private final String description;
}
