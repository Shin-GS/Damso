package com.damso.userservice.story;

import com.damso.userservice.story.request.StoryCommentCreateRequest;

public interface StoryCommentEditor {
    void createComment(Long storyId,
                       Long pageId,
                       Long memberId,
                       StoryCommentCreateRequest request);
}
