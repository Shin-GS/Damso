package com.damso.userservice.story.response;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.storage.entity.story.temporary.TemporaryStory;

public record StoryEditInfoResponse(Long id,
                                    String title,
                                    StoryCommentType commentType) {
    public static StoryEditInfoResponse of(TemporaryStory temporaryStory) {
        return new StoryEditInfoResponse(temporaryStory.getStoryId(),
                temporaryStory.getTitle(),
                temporaryStory.getCommentType());
    }
}
