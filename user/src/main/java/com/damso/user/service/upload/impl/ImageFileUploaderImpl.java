package com.damso.user.service.upload.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.file.FileUploadUtil;
import com.damso.core.utils.file.FileUtil;
import com.damso.user.service.upload.ImageFileUploader;
import com.damso.user.service.upload.response.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageFileUploaderImpl implements ImageFileUploader {
    @Value("${server.domain}")
    private String serverDomain;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.access.path}")
    private String accessPath;

    @Value("${file.upload.image.max-file-size}")
    private long maxFileSize;

    @Value("${file.upload.image.allowed-extensions}")
    private String[] allowedExtensions;

    @Override
    public FileUploadResponse upload(MultipartFile image, Long memberId) {
        FileUploadUtil.validateFile(image, maxFileSize, allowedExtensions);

        String uploadDir = FileUploadUtil.resolveUploadDir(uploadPath, memberId);
        if (!StringUtils.hasText(uploadDir)) {
            throw new BusinessException(ErrorCode.FILE_PATH_NOT_VALID);
        }

        String extension = FileUtil.getExtension(image.getOriginalFilename());
        String uniqueFileName = FileUploadUtil.createUniqueFileName() + "." + extension;
        Path targetPath = Paths.get(uploadDir, uniqueFileName);
        if (FileUploadUtil.copyFile(image, targetPath)) {
            return FileUploadResponse.of(FileUploadUtil.getAccessURL(serverDomain, accessPath, memberId, uniqueFileName));
        }

        throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
    }
}
