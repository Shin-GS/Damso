package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.entity.story.StoryPage;

public record StoryEditPageResponse(Long id,
                                    StoryType storyType) {
    public static StoryEditPageResponse of(StoryPage storyPage) {
        return new StoryEditPageResponse(storyPage.getId(),
                storyPage.getStoryType());
    }
}
