package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;

public record StoryEditPageResponse(Long id,
                                    StoryType storyType) {
    public static StoryEditPageResponse of(TemporaryStoryPage temporaryStoryPage) {
        return new StoryEditPageResponse(temporaryStoryPage.getId(),
                temporaryStoryPage.getStoryType());
    }
}
