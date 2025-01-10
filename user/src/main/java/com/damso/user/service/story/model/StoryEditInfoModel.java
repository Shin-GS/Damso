package com.damso.user.service.story.model;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryType;
import com.damso.core.utils.common.StringUtil;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.StoryFile;
import com.damso.domain.db.entity.story.StoryText;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public record StoryEditInfoModel(Long id,
                                 String title,
                                 StoryType storyType,
                                 String text,
                                 List<String> files,
                                 StoryCommentType commentType,
                                 boolean published) {
    public static StoryEditInfoModel of(Story story) {
        return new StoryEditInfoModel(story.getId(),
                story.getTitle(),
                story.getStoryType(),
                getText(story.getStoryText()),
                getFiles(story.getStoryFiles()),
                story.getCommentType(),
                story.isPublished());
    }

    private static String getText(StoryText storyText) {
        if (ObjectUtils.isEmpty(storyText)) {
            return "";
        }

        return StringUtil.defaultIfEmpty(storyText.getText(), "");
    }

    private static List<String> getFiles(List<StoryFile> storyFiles) {
        if (ObjectUtils.isEmpty(storyFiles)) {
            return new ArrayList<>();
        }

        return storyFiles.stream()
                .map(StoryFile::getFilePath)
                .toList();
    }
}
