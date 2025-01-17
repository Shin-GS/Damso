package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryCommentType implements CommonEnum {
    NONE("NONE", "댓글 없음", "누구도 댓글을 작성할 수 없음"),
    SUBSCRIBE("SUBSCRIBE", "구독자 댓글", "스토리를 구독한 구독자만 댓글 작성 가능"),
    ALL("ALL", "전체 댓글", "스토리의 구독여부와 상관없이 댓글 작성 가능");

    private final String code;
    private final String value;
    private final String description;
}
