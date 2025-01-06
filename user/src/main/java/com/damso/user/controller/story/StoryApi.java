package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.story.StoryEditor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryApi {
    private final StoryEditor storyEditor;

    @PostMapping
    public SuccessResponse createStory(@SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyEditor.create(memberId));
    }
}
