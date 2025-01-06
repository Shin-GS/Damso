package com.damso.user.service.upload.uploader;

import com.damso.core.constant.upload.UploadFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoFileUploaderImpl extends AbstractUploader {
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
    protected String getUploadPath() {
        return uploadPath;
    }

    @Override
    protected String getAccessPath() {
        return serverDomain + accessPath;
    }

    @Override
    protected long getMaxFileSize() {
        return maxFileSize;
    }

    @Override
    protected String[] getAllowedExtensions() {
        return allowedExtensions;
    }

    @Override
    public boolean isMatch(UploadFileType fileType) {
        return fileType == UploadFileType.VIDEO;
    }
}
