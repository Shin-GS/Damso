package com.damso.user.service.story;

import com.damso.domain.db.entity.story.Story;
import com.damso.user.service.story.response.StoryEditInfoResponse;

public interface StoryFinder {
    Story getEntity(Long storyId);

    Story getEditableEntity(Long storyId, Long memberId);

    StoryEditInfoResponse getEditInfo(Long storyId, Long memberId);
}
