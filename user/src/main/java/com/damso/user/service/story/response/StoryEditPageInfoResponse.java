package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryType;
import com.damso.core.utils.common.StringUtil;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;

import java.util.ArrayList;
import java.util.List;

public record StoryEditPageInfoResponse(StoryType storyType,
                                        String text,
                                        String planText,
                                        List<String> files) {
    public static StoryEditPageInfoResponse of(TemporaryStoryPage temporaryStoryPage) {
        return switch (temporaryStoryPage.getStoryType()) {
            case TEXT -> new StoryEditPageInfoResponse(
                    temporaryStoryPage.getStoryType(),
                    StringUtil.defaultIfEmpty(temporaryStoryPage.getTemporaryStoryText().getText(), ""),
                    StringUtil.defaultIfEmpty(temporaryStoryPage.getTemporaryStoryText().getPlanText(), ""),
                    new ArrayList<>()
            );

            case IMAGE -> new StoryEditPageInfoResponse(
                    temporaryStoryPage.getStoryType(),
                    "",
                    "",
                    temporaryStoryPage.getImagePaths()
            );

            case VIDEO -> new StoryEditPageInfoResponse(
                    temporaryStoryPage.getStoryType(),
                    "",
                    "",
                    temporaryStoryPage.getVideoPaths()
            );
        };
    }
}
