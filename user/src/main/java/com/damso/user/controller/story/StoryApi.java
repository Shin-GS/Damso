package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.request.StoryEditRequest;
import com.damso.user.service.story.response.StoryEditResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryApi {
    private final StoryEditor storyEditor;

    @PostMapping
    public SuccessResponse createStory(@SessionMemberId Long memberId) {
        StoryEditResponse storyEditResponse = storyEditor.create(memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, storyEditResponse);
    }

    @PutMapping("{storyId}")
    public SuccessResponse updateStory(@PathVariable("storyId") Long storyId,
                                       @SessionMemberId Long memberId,
                                       @RequestBody @Valid StoryEditRequest request) {
        StoryEditResponse storyEditResponse = storyEditor.update(storyId, memberId, request);
        return SuccessResponse.of(SuccessCode.SUCCESS, storyEditResponse);
    }
}
