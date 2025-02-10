package com.damso.userservice.story.response;

import com.damso.core.enums.story.StoryType;
import com.damso.storage.entity.story.content.StoryPage;

import java.util.List;

public record StoryViewPageResponse(Long storyId,
                                    StoryType storyType,
                                    String text,
                                    List<String> files,
                                    Long prevPageId,
                                    Long nextPageId) {
    public static StoryViewPageResponse of(StoryPage storyPage) {
        return new StoryViewPageResponse(storyPage.getStoryId(),
                storyPage.getStoryType(),
                storyPage.getStoryText(),
                storyPage.getStoryFilePaths(),
                storyPage.getPrevPageId(),
                storyPage.getNextPageId()
        );
    }
}
