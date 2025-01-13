package com.damso.user.service.story.response;

import com.damso.domain.db.entity.story.Story;

public record StoryEditResponse(Long storyId) {
    public static StoryEditResponse of(Story story) {
        return new StoryEditResponse(story.getId());
    }
}
