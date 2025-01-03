package com.damso.user.service.story.model;

import com.damso.core.constant.story.StoryCommentType;
import com.damso.core.constant.story.StoryType;
import com.damso.domain.db.entity.story.Story;

public record EditStoryModel(Long id,
                             String title,
                             StoryType storyType,
                             StoryCommentType commentType,
                             boolean published) {
    public static EditStoryModel of(Story story) {
        return new EditStoryModel(story.getId(),
                story.getTitle(),
                story.getStoryType(),
                story.getCommentType(),
                story.isPublished());
    }
}
