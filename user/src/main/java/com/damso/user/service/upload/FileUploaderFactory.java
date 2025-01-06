package com.damso.user.service.upload;

import com.damso.core.constant.upload.UploadFileType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.user.service.upload.uploader.AbstractUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploaderFactory {
    private final List<AbstractUploader> uploaders;

    public AbstractUploader factory(UploadFileType fileType) {
        if (fileType == null) {
            throw new BusinessException(ErrorCode.FILE_TYPE_NOT_ALLOWED);
        }

        return uploaders.stream()
                .filter(uploader -> uploader.isMatch(fileType))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_TYPE_NOT_ALLOWED));
    }
}