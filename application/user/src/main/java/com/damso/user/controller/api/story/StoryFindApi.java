package com.damso.user.controller.api.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.response.SuccessResponse;
import com.damso.core.code.SuccessCode;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StorySearchRequest;
import com.damso.userservice.story.response.StorySearchResponse;
import com.damso.userservice.story.response.StoryViewPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryFindApi {
    private final StoryFinder storyFinder;
    private final StoryPageFinder storyPageFinder;

    @GetMapping
    public SuccessResponse<List<StorySearchResponse>> storyList(StorySearchRequest request) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyFinder.findList(request));
    }

    @GetMapping("/{storyId}/pages/first-page")
    public SuccessResponse<StoryViewPageResponse> getFirstStoryPage(@PathVariable("storyId") Long storyId,
                                                                    @SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyPageFinder.getStoryPage(storyId, memberId));
    }

    @GetMapping("/{storyId}/pages/{pageId}")
    public SuccessResponse<StoryViewPageResponse> getStoryPage(@PathVariable("storyId") Long storyId,
                                                               @PathVariable("pageId") Long pageId,
                                                               @SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyPageFinder.getStoryPage(storyId, pageId, memberId));
    }
}
