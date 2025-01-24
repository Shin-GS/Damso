package com.damso.userservice.upload;

import com.damso.userservice.upload.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileUploader {
    FileUploadResponse upload(MultipartFile image, Long memberId);
}
