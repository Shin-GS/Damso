package com.damso.user.service.story.response;

import com.damso.domain.db.entity.story.temporary.TemporaryStory;

public record StoryEditResponse(Long storyId) {
    public static StoryEditResponse of(TemporaryStory temporaryStory) {
        return new StoryEditResponse(temporaryStory.getStoryId());
    }
}
