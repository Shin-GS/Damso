package com.damso.core.request.regex.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryRegexPattern implements CommonRegexPattern {
    TITLE("TITLE", "^[\\p{L}\\p{N}\\s]{1,255}$", "제목은 공백, 글자, 숫자만 허용됩니다"),
    STORY_TYPE("STORY_TYPE", "", "스토리 유형을 선택해주세요"),
    COMMENT_TYPE("COMMENT_TYPE", "", "댓글 유형을 선택해주세요");

    private final String code;
    private final String pattern;
    private final String message;
}
