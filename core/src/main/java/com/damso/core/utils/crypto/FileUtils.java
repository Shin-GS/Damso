package com.damso.core.utils.crypto;

import org.springframework.util.StringUtils;

import java.io.File;

public class FileUtils {
    private FileUtils() {
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

    public static String getName(String filename) {
        if (!StringUtils.hasText(filename)) {
            return null;
        }

        int index = filename.lastIndexOf(File.separator);
        return filename.substring(index + 1);
    }

    public static String getBaseName(String filename) {
        if (!StringUtils.hasText(filename)) {
            return null;
        }

        String name = getName(filename);
        if (!StringUtils.hasText(name)) {
            return null;
        }

        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name;
        }

        return name.substring(0, index);
    }

    public static String getFullPath(String filename) {
        if (!StringUtils.hasText(filename)) {
            return null;
        }

        int index = filename.lastIndexOf(File.separator);
        return (index == -1) ? "" : filename.substring(0, index);
    }
}
