package com.damso.user.service.story.command;

import com.damso.core.constant.story.StoryCommentType;
import com.damso.core.constant.story.StoryType;

import java.util.List;

public record StoryEditCommand(String title,
                               StoryType storyType,
                               String text,
                               String planText,
                               List<String> files,
                               StoryCommentType commentType,
                               boolean published) {
}
