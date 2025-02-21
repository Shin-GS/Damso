package com.damso.common.request.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryRegexPattern implements CommonRegexPattern {
    TITLE("TITLE", "^[a-zA-Z0-9\\s\\-가-힣ㄱ-ㅎㅏ-ㅣ]{1,255}$", "^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9\\s\\-]{1,255}$", "제목은 공백, 한글, 영문, 숫자, '-'만 허용됩니다"),
    STORY_TYPE("STORY_TYPE", "", "", "스토리 유형을 선택해주세요"),
    COMMENT_TYPE("COMMENT_TYPE", "", "", "댓글 유형을 선택해주세요"),
    COMMENT_TEXT("COMMENT_TEXT", "", "", "댓글을 입력해주세요"),
    STORY_ID("STORY_ID", "", "", "스토리 번호를 입력해주세요"),
    PAGE_ID("PAGE_ID", "", "", "페이지 번호를 입력해주세요");

    private final String code;
    private final String pattern;
    private final String fePattern;
    private final String message;
}
