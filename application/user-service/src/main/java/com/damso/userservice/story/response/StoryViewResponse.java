package com.damso.userservice.story.response;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;

public record StoryViewResponse(Long id,
                                String title,
                                StoryCommentType commentType,
                                boolean editable) {
    public static StoryViewResponse of(Story story,
                                       Member member) {
        return new StoryViewResponse(story.getId(),
                story.getTitle(),
                story.getCommentType(),
                story.isEditable(member)
        );
    }
}
