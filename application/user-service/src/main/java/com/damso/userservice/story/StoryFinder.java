package com.damso.userservice.story;

import com.damso.storage.entity.story.Story;

public interface StoryFinder {
    Story getEntity(Long storyId);

    Story getEditableEntity(Long storyId, Long memberId);
}
