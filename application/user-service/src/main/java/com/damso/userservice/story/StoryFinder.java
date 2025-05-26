package com.damso.userservice.story;

import com.damso.storage.entity.story.Story;
import com.damso.userservice.story.request.StorySearchRequest;
import com.damso.userservice.story.response.StorySearchResponse;
import com.damso.userservice.story.response.StoryViewResponse;

import java.util.List;

public interface StoryFinder {
    Story getEntity(Long storyId);

    Story getEditableEntity(Long storyId);

    Story getEditableEntity(Long storyId,
                            Long memberId);

    StoryViewResponse getStoryView(Long storyId,
                                   Long memberId);

    List<StorySearchResponse> findList(StorySearchRequest request);
}
