package com.damso.userservice.story.response;

import com.damso.storage.entity.story.comment.StoryComment;

public record StoryCommentEditResponse(Long id,
                                       Long storyId,
                                       Long storyPageId) {
    public StoryCommentEditResponse(StoryComment storyComment) {
        this(storyComment.getId(), storyComment.getStoryId(), storyComment.getStoryPageId());
    }
}
