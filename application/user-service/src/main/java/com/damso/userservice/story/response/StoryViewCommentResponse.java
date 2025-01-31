package com.damso.userservice.story.response;

public record StoryViewCommentResponse(String username,
                                       String text) {
    public static StoryViewCommentResponse of() {
        return new StoryViewCommentResponse("", "");
    }
}
