package com.damso.userservice.upload;

import com.damso.userservice.upload.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoFileUploader {
    FileUploadResponse upload(MultipartFile video, Long memberId);
}
