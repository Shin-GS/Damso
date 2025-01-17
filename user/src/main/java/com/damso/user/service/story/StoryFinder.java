package com.damso.user.service.story;

import com.damso.domain.db.entity.story.Story;

public interface StoryFinder {
    Story getEntity(Long storyId);

    Story getEditableEntity(Long storyId, Long memberId);
}
