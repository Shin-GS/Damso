package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.command.StoryEditCommand;
import com.damso.user.service.story.model.StoryEditModel;
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
        StoryEditModel model = storyEditor.create(memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, model);
    }

    @PutMapping("{storyId}")
    public SuccessResponse updateStory(@PathVariable("storyId") Long storyId,
                                       @SessionMemberId Long memberId,
                                       @RequestBody @Valid StoryEditCommand command) {
        StoryEditModel model = storyEditor.update(storyId, memberId, command);
        return SuccessResponse.of(SuccessCode.SUCCESS, model);
    }
}
