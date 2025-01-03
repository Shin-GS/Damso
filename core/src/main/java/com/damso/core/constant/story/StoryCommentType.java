package com.damso.core.constant.story;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoryCommentType implements EnumInfo {
    NONE("댓글 없음"),
    PAGE("페이지 별로 댓글 작성 가능 ex)컷툰"),
    ALL("콘텐츠 전체에 댓글 작성 가능");

    private final String description;
}
