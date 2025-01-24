package com.damso.userservice.story.response;

import com.damso.storage.entity.story.temporary.TemporaryStory;

public record StoryEditResponse(Long storyId) {
    public static StoryEditResponse of(TemporaryStory temporaryStory) {
        return new StoryEditResponse(temporaryStory.getStoryId());
    }
}
