package com.damso.user.service.story.request;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryType;
import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.StoryRegexPattern;

import java.util.List;

public record StoryEditRequest(
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "TITLE", notEmpty = true) String title,
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "STORY_TYPE", notEmpty = true) StoryType storyType,
        String text,
        String planText,
        List<String> files,
        @ValidPattern(value = StoryRegexPattern.class, fieldCode = "COMMENT_TYPE", notEmpty = true) StoryCommentType commentType,
        Boolean published) {
}
