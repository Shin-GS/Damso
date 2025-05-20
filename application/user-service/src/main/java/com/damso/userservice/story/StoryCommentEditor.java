package com.damso.userservice.story;

import com.damso.userservice.story.request.StoryCommentCreateRequest;
import com.damso.userservice.story.request.StoryCommentUpdateRequest;
import com.damso.userservice.story.response.StoryCommentEditResponse;

public interface StoryCommentEditor {
    StoryCommentEditResponse createComment(StoryCommentCreateRequest request,
                                           Long memberId);

    StoryCommentEditResponse updateComment(Long commentId,
                                           StoryCommentUpdateRequest request,
                                           Long memberId);

    StoryCommentEditResponse deleteComment(Long commentId,
                                           Long memberId);
}
