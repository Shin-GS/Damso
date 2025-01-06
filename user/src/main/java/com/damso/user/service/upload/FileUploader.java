package com.damso.user.service.upload;

import com.damso.user.service.upload.model.FileUploadModel;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    FileUploadModel upload(String fileType,
                           MultipartFile file,
                           Long memberId);
}
