package com.damso.user.service.upload;

import com.damso.user.service.upload.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoFileUploader {
    FileUploadResponse upload(MultipartFile video, Long memberId);
}
