package com.damso.user.service.story;

import com.damso.user.service.story.request.StoryEditRequest;
import com.damso.user.service.story.response.StoryEditResponse;

public interface StoryEditor {
    StoryEditResponse create(Long memberId);

    StoryEditResponse update(Long storyId,
                             Long memberId,
                             StoryEditRequest request);
}
