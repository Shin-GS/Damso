package com.damso.core.constant.upload;

import com.damso.core.constant.EnumInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UploadFileType implements EnumInfo {
    IMAGE("IMAGE"),
    VIDEO("VIDEO");

    private final String description;

    public static UploadFileType match(String value) {
        return Arrays.stream(UploadFileType.values())
                .filter(fileType -> fileType.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
