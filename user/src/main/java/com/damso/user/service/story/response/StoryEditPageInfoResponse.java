package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;

import java.util.ArrayList;
import java.util.List;

public record StoryEditPageInfoResponse(Long storyId,
                                        Long temporaryStoryPageId,
                                        StoryType storyType,
                                        String text,
                                        List<String> files) {
    public static StoryEditPageInfoResponse of(Long storyId,
                                               TemporaryStoryPage temporaryStoryPage) {
        return switch (temporaryStoryPage.getStoryType()) {
            case TEXT -> new StoryEditPageInfoResponse(
                    storyId,
                    temporaryStoryPage.getId(),
                    temporaryStoryPage.getStoryType(),
                    temporaryStoryPage.getStoryText(),
                    new ArrayList<>()
            );

            case IMAGE -> new StoryEditPageInfoResponse(
                    storyId,
                    temporaryStoryPage.getId(),
                    temporaryStoryPage.getStoryType(),
                    "",
                    temporaryStoryPage.getImagePaths()
            );

            case VIDEO -> new StoryEditPageInfoResponse(
                    storyId,
                    temporaryStoryPage.getId(),
                    temporaryStoryPage.getStoryType(),
                    "",
                    temporaryStoryPage.getVideoPaths()
            );
        };
    }
}
