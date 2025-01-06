package com.damso.user.service.upload;

import com.damso.core.constant.upload.UploadFileType;
import com.damso.user.service.upload.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploader {
    private final FileUploaderFactory fileUploaderFactory;

    @Override
    public FileUploadModel upload(String fileType,
                                  MultipartFile file,
                                  Long memberId) {
        return fileUploaderFactory.factory(UploadFileType.match(fileType))
                .upload(file, memberId);
    }
}
