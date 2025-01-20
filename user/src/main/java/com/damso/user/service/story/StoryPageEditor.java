package com.damso.user.service.story;

import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.request.StoryPageEditRequest;
import com.damso.user.service.story.request.StoryPageReorderRequest;

import java.util.List;

public interface StoryPageEditor {
    void create(Long storyId,
                Long memberId);

    void delete(Long storyId,
                Long memberId,
                Long temporaryStoryPageId);

    void reorder(Long storyId,
                 Long memberId,
                 List<StoryPageReorderRequest.StoryPageOrderRequest> pageOrders);

    void updateType(Long storyId,
                    Long memberId,
                    Long temporaryStoryPageId,
                    StoryType storyType);

    void update(Long storyId,
                Long memberId,
                Long temporaryStoryPageId,
                StoryPageEditRequest request);
}
