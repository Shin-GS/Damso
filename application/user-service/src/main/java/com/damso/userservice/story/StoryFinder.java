package com.damso.userservice.story;

import com.damso.storage.entity.story.Story;
import com.damso.userservice.story.response.StoryViewResponse;

public interface StoryFinder {
    Story getEntity(Long storyId);

    Story getEditableEntity(Long storyId);

    Story getEditableEntity(Long storyId,
                            Long memberId);

    StoryViewResponse getStoryView(Long storyId,
                                   Long memberId);
}
