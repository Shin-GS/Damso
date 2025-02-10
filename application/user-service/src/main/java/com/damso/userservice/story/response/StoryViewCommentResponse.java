package com.damso.userservice.story.response;

import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.comment.StoryComment;

public record StoryViewCommentResponse(String username,
                                       String text,
                                       boolean editable) {
    public static StoryViewCommentResponse of(StoryComment comment,
                                              Member member) {
        return new StoryViewCommentResponse(comment.getMemberName(), comment.getText(), comment.isEditable(member));
    }
}
