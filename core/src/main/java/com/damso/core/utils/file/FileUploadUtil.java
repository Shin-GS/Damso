package com.damso.core.utils.file;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUploadUtil {
    private FileUploadUtil() {
    }

    public static String createUniqueFileName() {
        return UUID.randomUUID() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static void validateFile(MultipartFile file, long maxFileSize, String[] allowedExtensions) {
        if (file == null || file.isEmpty() || !StringUtils.hasText(file.getOriginalFilename())) {
            throw new BusinessException(ErrorCode.FILE_EMPTY);
        }

        if (file.getSize() > maxFileSize) {
            throw new BusinessException(ErrorCode.FILE_SIZE_EXCEEDED);
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if (!StringUtils.hasText(originalFilename) || originalFilename.contains("..")) {
            throw new BusinessException(ErrorCode.FILE_NAME_INVALID);
        }

        String extension = FileUtil.getExtension(originalFilename);
        if (!(extension != null && Arrays.stream(allowedExtensions).anyMatch(allowed -> allowed.equalsIgnoreCase(extension)))) {
            throw new BusinessException(ErrorCode.FILE_EXTENSION_NOT_ALLOWED);
        }
    }

    public static String resolveUploadDir(String basePath,
                                          Long memberId) {
        String uploadDir = Paths.get(basePath,
                memberId.toString(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ).toString();

        File directory = new File(uploadDir);
        if (directory.exists() || directory.mkdirs()) {
            return uploadDir;
        }

        return null;
    }

    public static String getAccessURL(String serverDomain,
                                      String accessPath,
                                      Long memberId,
                                      String uniqueFileName) {
        String basePath = serverDomain + accessPath;
        return String.join("/",
                List.of(basePath,
                        memberId.toString(),
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        uniqueFileName)
        );
    }

    public static boolean copyFile(MultipartFile file, Path targetPath) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath);
            return Boolean.TRUE;

        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
