package com.damso.user.controller.api.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.response.SuccessResponse;
import com.damso.core.code.SuccessCode;
import com.damso.core.enums.story.StoryType;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StoryPageEditRequest;
import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories/edit/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageApi {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;

    @GetMapping
    public SuccessResponse<List<StoryEditPageResponse>> getPages(@PathVariable("storyId") Long storyId,
                                                                 @SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyPageFinder.resolveTemporaryStoryPages(storyId, memberId));
    }

    @PostMapping
    public SuccessResponse<Object> createPage(@PathVariable("storyId") Long storyId,
                                              @SessionMemberId Long memberId) {
        storyPageEditor.create(storyId, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/first-page")
    public SuccessResponse<StoryEditPageInfoResponse> getFirstPageContent(@PathVariable("storyId") Long storyId,
                                                                          @SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyPageFinder.getFirstTemporaryStoryPageInfo(storyId, memberId));
    }

    @GetMapping("/{temporaryStoryPageId}")
    public SuccessResponse<StoryEditPageInfoResponse> getPageContent(@PathVariable("storyId") Long storyId,
                                                                     @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                                                     @SessionMemberId Long memberId) {
        StoryEditPageInfoResponse storyPageInfo = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageId);
        return SuccessResponse.of(SuccessCode.SUCCESS, storyPageInfo);
    }

    @PutMapping("/{temporaryStoryPageId}")
    public SuccessResponse<Object> updatePage(@PathVariable("storyId") Long storyId,
                                              @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                              @RequestBody @Valid StoryPageEditRequest request,
                                              @SessionMemberId Long memberId) {
        storyPageEditor.update(storyId, memberId, temporaryStoryPageId, request);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @PutMapping("/{temporaryStoryPageId}/type")
    public SuccessResponse<Object> updatePageType(@PathVariable("storyId") Long storyId,
                                                  @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                                  @ModelAttribute @Valid StoryType storyType,
                                                  @SessionMemberId Long memberId) {
        storyPageEditor.updateType(storyId, memberId, temporaryStoryPageId, storyType);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/{temporaryStoryPageId}")
    public SuccessResponse<Long> deletePage(@PathVariable("storyId") Long storyId,
                                            @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                            @SessionMemberId Long memberId) {
        Long temporaryStoryPageIdToMove = storyPageEditor.delete(storyId, memberId, temporaryStoryPageId);
        return SuccessResponse.of(SuccessCode.SUCCESS, temporaryStoryPageIdToMove);
    }
}
