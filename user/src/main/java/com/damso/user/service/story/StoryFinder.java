package com.damso.user.service.story;

import com.damso.domain.db.entity.story.Story;
import com.damso.user.service.story.response.StoryEditInfoResponse;

public interface StoryFinder {
    Story getEntity(Long storyId);

    StoryEditInfoResponse getEditInfo(Long storyId, Long memberId);
}
