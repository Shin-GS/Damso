package com.damso.user.service.story;

import com.damso.user.service.story.response.StoryEditPageResponse;

import java.util.List;

public interface StoryPageFinder {
    List<StoryEditPageResponse> getPages(Long storyId, Long memberId);
}
