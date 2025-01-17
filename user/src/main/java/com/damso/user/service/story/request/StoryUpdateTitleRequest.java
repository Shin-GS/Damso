package com.damso.user.service.story.request;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.StoryRegexPattern;

public record StoryUpdateTitleRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "TITLE", notEmpty = true) String title) {
}
