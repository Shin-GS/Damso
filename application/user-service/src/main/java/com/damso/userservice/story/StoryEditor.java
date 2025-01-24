package com.damso.userservice.story;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.temporary.TemporaryStory;
import com.damso.userservice.story.response.StoryEditInfoResponse;
import com.damso.userservice.story.response.StoryEditResponse;

public interface StoryEditor {
    StoryEditResponse create(Long memberId);

    StoryEditInfoResponse resolveTemporaryEditInfo(Long storyId,
                                                   Long memberId);

    void updateTitle(Long storyId,
                     Long memberId,
                     String title);

    void updateCommentType(Long storyId,
                           Long memberId,
                           StoryCommentType commentType);

    void reset(Long storyId,
               Long memberId);

    void published(Long storyId,
                   Long memberId);

    void delete(Long storyId,
                Long memberId);

    TemporaryStory resolveTemporaryStory(Story story);
}
