package com.damso.userservice.story.request;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.StoryRegexPattern;

public record StoryUpdateCommentTypeRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "COMMENT_TYPE", notEmpty = true) StoryCommentType commentType) {
}
