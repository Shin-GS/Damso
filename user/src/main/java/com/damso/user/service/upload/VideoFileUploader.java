package com.damso.user.service.upload;

import com.damso.user.service.upload.model.FileUploadModel;
import org.springframework.web.multipart.MultipartFile;

public interface VideoFileUploader {
    FileUploadModel upload(MultipartFile video, Long memberId);
}
