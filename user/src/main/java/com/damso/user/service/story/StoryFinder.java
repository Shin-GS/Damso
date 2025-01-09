package com.damso.user.service.story;

import com.damso.domain.db.entity.story.Story;
import com.damso.user.service.story.model.StoryEditInfoModel;

public interface StoryFinder {
    Story getEntity(Long storyId);

    StoryEditInfoModel getEditInfo(Long storyId, Long memberId);
}
