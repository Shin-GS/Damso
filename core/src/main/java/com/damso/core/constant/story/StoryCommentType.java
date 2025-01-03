package com.damso.core.constant.story;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoryCommentType implements EnumInfo {
    NONE("댓글 없음"),
    CUT("컷툰 스타일 댓글 작성 가능"),
    ALL("스토리 전체에 댓글 작성 가능");

    private final String description;
}
