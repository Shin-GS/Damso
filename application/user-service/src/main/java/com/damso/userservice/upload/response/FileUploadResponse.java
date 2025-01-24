package com.damso.userservice.upload.response;

public record FileUploadResponse(String url) {
    public static FileUploadResponse of(String url) {
        return new FileUploadResponse(url);
    }
}
