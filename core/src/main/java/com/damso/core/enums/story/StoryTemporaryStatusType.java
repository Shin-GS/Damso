package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryTemporaryStatusType implements CommonEnum {
    WRITING("WRITING", "작성중", "스토리를 작성중"),
    PUBLISHED("PUBLISHED", "반영완료", "수정한 스토리를 반영 완료"),
    RESET("RESET", "초기화", "수정한 스토리의 변경사항을 초기화 완료"),
    DELETED("DELETED", "삭제", "스토리 삭제 후 반영 완료");

    private final String code;
    private final String value;
    private final String description;
}
