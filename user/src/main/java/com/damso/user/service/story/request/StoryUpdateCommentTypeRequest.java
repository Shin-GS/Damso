package com.damso.user.service.story.request;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.StoryRegexPattern;

public record StoryUpdateCommentTypeRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "COMMENT_TYPE", notEmpty = true) StoryCommentType commentType) {
}
