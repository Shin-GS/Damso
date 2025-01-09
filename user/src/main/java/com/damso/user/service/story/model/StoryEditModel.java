package com.damso.user.service.story.model;

import com.damso.domain.db.entity.story.Story;

public record StoryEditModel(Long storyId) {
    public static StoryEditModel of(Story story) {
        return new StoryEditModel(story.getId());
    }
}
