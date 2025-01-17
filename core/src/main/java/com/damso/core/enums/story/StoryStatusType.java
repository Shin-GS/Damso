package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryStatusType implements CommonEnum {
    CREATED("CREATED", "생성완료", "스토리를 생성만 하고 수정 및 발행을 하지 않음"),
    PUBLISHED("PUBLISHED", "발행중", "스토리를 발행중이며 조회, 수정, 댓글 작성 가능"),
    DELETED("DELETED", "삭제완료", "스토리를 삭제했으며 모든 기능 사용 불가");

    private final String code;
    private final String value;
    private final String description;
}
