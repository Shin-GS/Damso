package com.damso.userservice.story.request;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.StoryRegexPattern;

public record StoryUpdateTitleRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "TITLE", notEmpty = true) String title) {
}
