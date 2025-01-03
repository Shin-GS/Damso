package com.damso.user.service.story.model;

public record CreateStoryModel(Long storyId) {
    public static CreateStoryModel of(Long storyId) {
        return new CreateStoryModel(storyId);
    }
}
