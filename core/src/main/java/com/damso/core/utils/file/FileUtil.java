package com.damso.core.utils.file;

import org.springframework.util.StringUtils;

public class FileUtil {
    private FileUtil() {
    }

    public static String getExtension(String filename) {
        if (!StringUtils.hasText(filename)) {
            return null;
        }

        int index = filename.lastIndexOf('.');
        if (index == -1 || index == filename.length() - 1) {
            return "";  // 확장자가 없거나 마지막이 .인 경우
        }

        return filename.substring(index + 1);
    }
}
