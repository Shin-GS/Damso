package com.damso.user.service.story;

import com.damso.user.service.story.request.StoryPageReorderRequest;

import java.util.List;

public interface StoryPageEditor {
    void createPage(Long storyId,
                    Long memberId);

    void deletePage(Long storyId,
                    Long memberId,
                    Long storyPageId);

    void reorderPage(Long storyId,
                     Long memberId,
                     List<StoryPageReorderRequest.StoryPageOrderRequest> pageOrders);
}
