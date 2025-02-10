package com.damso.core.enums.story;

import com.damso.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoryCommentStatusType implements CommonEnum {
    NORMAL("NORMAL", "정상 댓글", "정상적인 댓글 상태"),
    REPORTED("REPORTED", "신고된 댓글", "누군가의 신고로 비노출된 댓글 상태"),
    DELETED("DELETED", "삭제된 댓글", "댓글 작성자가 댓글을 삭제한 상태");

    private final String code;
    private final String value;
    private final String description;
}
