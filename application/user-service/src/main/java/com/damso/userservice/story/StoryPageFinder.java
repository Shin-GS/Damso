package com.damso.userservice.story;

import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;

import java.util.List;

public interface StoryPageFinder {
    List<StoryEditPageResponse> getTemporaryStoryPages(Long storyId,
                                                       Long memberId);

    StoryEditPageInfoResponse getFirstTemporaryStoryPageInfo(Long storyId,
                                                             Long memberId);

    StoryEditPageInfoResponse getTemporaryStoryPageInfo(Long storyId,
                                                        Long memberId,
                                                        Long temporaryStoryPageId);
}
