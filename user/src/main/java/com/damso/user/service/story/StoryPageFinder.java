package com.damso.user.service.story;

import com.damso.user.service.story.response.StoryEditPageInfoResponse;
import com.damso.user.service.story.response.StoryEditPageResponse;

import java.util.List;

public interface StoryPageFinder {
    List<StoryEditPageResponse> getTemporaryStoryPages(Long storyId, Long memberId);

    StoryEditPageInfoResponse getTemporaryStoryPageInfo(Long storyId, Long memberId, Long storyPageId);
}
