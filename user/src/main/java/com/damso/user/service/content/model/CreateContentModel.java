package com.damso.user.service.content.model;

public record CreateContentModel(Long contentId) {
    public static CreateContentModel of(Long contentId) {
        return new CreateContentModel(contentId);
    }
}
