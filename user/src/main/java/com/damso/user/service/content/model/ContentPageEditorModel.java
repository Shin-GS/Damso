package com.damso.user.service.content.model;

public record ContentPageEditorModel(Long id,
                                     String type) {
    public static ContentPageEditorModel of(Long id, String type) {
        return new ContentPageEditorModel(id, type);
    }
}
