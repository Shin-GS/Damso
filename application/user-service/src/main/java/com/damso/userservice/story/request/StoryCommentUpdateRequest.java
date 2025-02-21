package com.damso.userservice.story.request;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.StoryRegexPattern;

public record StoryCommentUpdateRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "COMMENT_TEXT", notEmpty = true) String text) {
}
