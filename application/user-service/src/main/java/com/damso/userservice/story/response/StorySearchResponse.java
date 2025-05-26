package com.damso.userservice.story.response;

import com.damso.storage.entity.story.Story;

import java.util.List;

public record StorySearchResponse(Long id,
                                  String title,
                                  String creatorName,
                                  String thumbnailUrl,
                                  boolean like,
                                  List<String> hashTags) {
    public static StorySearchResponse of(Story story) {
        return new StorySearchResponse(
                story.getId(),
                story.getTitle(),
                story.getCreatorName(),
                "",
                Boolean.FALSE,
                List.of("취미", "게임")
        );
    }
}
