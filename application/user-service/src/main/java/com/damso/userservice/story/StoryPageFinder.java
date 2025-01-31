package com.damso.userservice.story;

import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import com.damso.userservice.story.response.StoryViewPageResponse;

import java.util.List;

public interface StoryPageFinder {
    List<StoryEditPageResponse> getTemporaryStoryPages(Long storyId,
                                                       Long memberId);

    StoryEditPageInfoResponse getFirstTemporaryStoryPageInfo(Long storyId,
                                                             Long memberId);

    StoryEditPageInfoResponse getTemporaryStoryPageInfo(Long storyId,
                                                        Long memberId,
                                                        Long temporaryStoryPageId);

    StoryViewPageResponse getStoryPage(Long storyId,
                                       Long memberId);

    StoryViewPageResponse getStoryPage(Long storyId,
                                       Long memberId,
                                       Long storyPageId);

    List<StoryViewCommentResponse> getPageComments(Long storyId,
                                                   Long memberId,
                                                   Long storyPageId);
}
