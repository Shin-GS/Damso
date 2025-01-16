package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.domain.db.entity.story.Story;

public record StoryEditInfoResponse(Long id,
                                    String title,
                                    StoryCommentType commentType,
                                    boolean published) {
    public static StoryEditInfoResponse of(Story story) {
        return new StoryEditInfoResponse(story.getId(),
                story.getTitle(),
                story.getCommentType(),
                story.isPublished());
    }
}
