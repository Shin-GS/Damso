package com.damso.user.service.upload.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.file.FileUplaodUtil;
import com.damso.core.utils.file.FileUtil;
import com.damso.user.service.upload.VideoFileUploader;
import com.damso.user.service.upload.model.FileUploadModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class VideoFileUploaderImpl implements VideoFileUploader {
    @Value("${server.domain}")
    private String serverDomain;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.access.path}")
    private String accessPath;

    @Value("${file.upload.video.max-file-size}")
    private long maxFileSize;

    @Value("${file.upload.video.allowed-extensions}")
    private String[] allowedExtensions;

    @Override
    public FileUploadModel upload(MultipartFile video, Long memberId) {
        FileUplaodUtil.validateFile(video, maxFileSize, allowedExtensions);

        String uploadDir = FileUplaodUtil.resolveUploadDir(uploadPath, memberId);
        if (!StringUtils.hasText(uploadDir)) {
            throw new BusinessException(ErrorCode.FILE_PATH_NOT_VALID);
        }

        String extension = FileUtil.getExtension(video.getOriginalFilename());
        String uniqueFileName = FileUplaodUtil.createUniqueFileName() + "." + extension;
        Path targetPath = Paths.get(uploadDir, uniqueFileName);
        if (FileUplaodUtil.copyFile(video, targetPath)) {
            return FileUploadModel.of(FileUplaodUtil.getAccessURL(serverDomain, accessPath, memberId, uniqueFileName));
        }

        throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
    }
}
