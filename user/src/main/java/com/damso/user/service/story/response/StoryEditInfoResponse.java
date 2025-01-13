package com.damso.user.service.story.response;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryType;
import com.damso.core.utils.common.StringUtil;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.StoryFile;
import com.damso.domain.db.entity.story.StoryText;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public record StoryEditInfoResponse(Long id,
                                    String title,
                                    StoryType storyType,
                                    String text,
                                    List<String> images,
                                    String video,
                                    StoryCommentType commentType,
                                    boolean published) {
    public static StoryEditInfoResponse of(Story story) {
        return new StoryEditInfoResponse(story.getId(),
                story.getTitle(),
                story.getStoryType(),
                getText(story.getStoryText()),
                getImages(story.getStoryFiles()),
                getVideo(story.getStoryFiles()),
                story.getCommentType(),
                story.isPublished());
    }

    private static String getText(StoryText storyText) {
        if (ObjectUtils.isEmpty(storyText)) {
            return "";
        }

        return StringUtil.defaultIfEmpty(storyText.getText(), "");
    }

    private static List<String> getImages(List<StoryFile> storyFiles) {
        if (ObjectUtils.isEmpty(storyFiles)) {
            return new ArrayList<>();
        }

        return storyFiles.stream()
                .filter(StoryFile::isImage)
                .map(StoryFile::getFilePath)
                .toList();
    }

    private static String getVideo(List<StoryFile> storyFiles) {
        if (ObjectUtils.isEmpty(storyFiles)) {
            return null;
        }

        return storyFiles.stream()
                .filter(StoryFile::isVideo)
                .map(StoryFile::getFilePath)
                .findFirst()
                .orElse(null);
    }
}
