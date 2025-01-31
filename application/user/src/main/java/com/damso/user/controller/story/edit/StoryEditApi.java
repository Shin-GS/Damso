package com.damso.user.controller.story.edit;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.core.code.SuccessCode;
import com.damso.common.response.SuccessResponse;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.request.StoryPageReorderRequest;
import com.damso.userservice.story.response.StoryEditResponse;
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
