package com.damso.userservice.story.request;

import java.util.List;

public record StoryPageReorderRequest(List<StoryPageOrderRequest> pageOrders) {
    public record StoryPageOrderRequest(Long storyPageId, int order) {
    }
}
