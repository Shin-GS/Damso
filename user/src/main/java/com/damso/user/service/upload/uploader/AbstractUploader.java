package com.damso.user.service.upload.uploader;

import com.damso.core.constant.upload.UploadFileType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.FileUtils;
import com.damso.user.service.upload.model.FileUploadModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class AbstractUploader {
    protected abstract String getUploadPath();

    protected abstract String getAccessPath();

    protected abstract long getMaxFileSize();

    protected abstract String[] getAllowedExtensions();

    public abstract boolean isMatch(UploadFileType fileType);

    public FileUploadModel upload(MultipartFile file, Long memberId) {
        validateFile(file);

        String uniqueFileName = createUniqueFileName(file);
        Path targetPath = Paths.get(createUploadDir(memberId), uniqueFileName);
        saveFile(file, targetPath);

        return FileUploadModel.of(getUploadedURL(memberId, uniqueFileName));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty() || !StringUtils.hasText(file.getOriginalFilename())) {
            throw new BusinessException(ErrorCode.FILE_EMPTY);
        }

        if (file.getSize() > getMaxFileSize()) {
            throw new BusinessException(ErrorCode.FILE_SIZE_EXCEEDED);
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFilename.contains("..")) {
            throw new BusinessException(ErrorCode.FILE_NAME_INVALID);
        }

        String extension = FileUtils.getExtension(originalFilename);
        if (!isAllowedExtension(extension)) {
            throw new BusinessException(ErrorCode.FILE_EXTENSION_NOT_ALLOWED);
        }
    }

    private boolean isAllowedExtension(String extension) {
        return extension != null &&
                Arrays.stream(getAllowedExtensions()).anyMatch(allowed -> allowed.equalsIgnoreCase(extension));
    }

    private String createUploadDir(Long memberId) {
        String uploadPath = Paths.get(
                getUploadPath(),
                memberId.toString(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ).toString();

        File directory = new File(uploadPath);
        if (directory.exists()) {
            return uploadPath;
        }

        if (directory.mkdirs()) {
            log.info("Directory created successfully.");
            return uploadPath;
        }

        log.error("Failed to create directory.");
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String createUniqueFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    private void saveFile(MultipartFile file, Path targetPath) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath);
            log.info("파일 업로드 성공: {}", targetPath);

        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUploadedURL(Long memberId, String uniqueFileName) {
        return String.join("/",
                List.of(getAccessPath(),
                        memberId.toString(),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        uniqueFileName)
        );
    }
}
