package com.damso.user.service.upload.model;

public record FileUploadModel(String url) {
    public static FileUploadModel of(String url) {
        return new FileUploadModel(url);
    }
}
