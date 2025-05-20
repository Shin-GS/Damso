package com.damso.user.controller.hx.reqeust;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.StoryRegexPattern;

public record StoryCommentCreateHxRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "STORY_ID", notEmpty = true) Long storyId,
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "PAGE_ID", notEmpty = true) Long storyPageId,
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "COMMENT_TEXT", notEmpty = true) String text) {
}
