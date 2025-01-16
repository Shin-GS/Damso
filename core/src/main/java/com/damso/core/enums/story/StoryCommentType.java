package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryCommentType implements CommonEnum {
    NONE("NONE", "댓글 없음", "구독자가 댓글을 작성할 수 없음"),
    // todo 페이지별로 댓글 작성
    // todo 구독자만 댓글 작성
    ALL("ALL", "전체 댓글", "구독자가 스토리 전체에 댓글 작성 가능");

    private final String code;
    private final String value;
    private final String description;
}
