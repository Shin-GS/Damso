package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.request.StoryPageReorderRequest;
import com.damso.user.service.story.response.StoryEditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryEditApi {
    private final StoryEditor storyEditor;
    private final StoryPageEditor storyPageEditor;

    @PostMapping
    public SuccessResponse createStory(@SessionMemberId Long memberId) {
        StoryEditResponse storyEditResponse = storyEditor.create(memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, storyEditResponse);
    }

    @PutMapping("/{storyId}/pages/reorder")
    public SuccessResponse reorder(@PathVariable("storyId") Long storyId,
                                   @RequestBody StoryPageReorderRequest request,
                                   @SessionMemberId Long memberId) {
        storyPageEditor.reorder(storyId, memberId, request.pageOrders());
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
