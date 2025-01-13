package com.damso.user.service.upload.response;

public record FileUploadResponse(String url) {
    public static FileUploadResponse of(String url) {
        return new FileUploadResponse(url);
    }
}
