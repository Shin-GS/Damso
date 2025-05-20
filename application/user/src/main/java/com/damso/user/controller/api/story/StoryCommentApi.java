package com.damso.user.controller.api.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.response.SuccessResponse;
import com.damso.core.code.SuccessCode;
import com.damso.userservice.story.StoryCommentEditor;
import com.damso.userservice.story.StoryCommentFinder;
import com.damso.userservice.story.request.StoryCommentUpdateRequest;
import com.damso.userservice.story.response.StoryCommentEditResponse;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryCommentApi {
    private final StoryCommentFinder storyCommentFinder;
    private final StoryCommentEditor storyCommentEditor;

    @GetMapping("/{storyId}/pages/{pageId}/comments")
    public SuccessResponse<List<StoryViewCommentResponse>> findComments(@PathVariable("storyId") Long storyId,
                                                                        @PathVariable("pageId") Long pageId,
                                                                        @SessionMemberId Long memberId,
                                                                        @PageableDefault(size = 30) Pageable pageable) {
        return SuccessResponse.of(SuccessCode.SUCCESS, storyCommentFinder.findComments(storyId, pageId, memberId, pageable));
    }

    @PostMapping("/{storyId}/pages/{pageId}/comments")
    public SuccessResponse<StoryCommentEditResponse> createComment(@PathVariable("storyId") Long storyId,
                                                                   @PathVariable("pageId") Long pageId,
                                                                   @RequestBody @Valid StoryCommentUpdateRequest request,
                                                                   @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentEditor.createComment(storyId, pageId, request, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, story);
    }

    @PutMapping("/pages/comments/{commentId}")
    public SuccessResponse<StoryCommentEditResponse> updateComment(@PathVariable("commentId") Long commentId,
                                                                   @ModelAttribute @Valid StoryCommentUpdateRequest request,
                                                                   @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentEditor.updateComment(commentId, request, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, story);
    }

    @DeleteMapping("/pages/comments/{commentId}")
    public SuccessResponse<StoryCommentEditResponse> deleteComment(@PathVariable("commentId") Long commentId,
                                                                   @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentEditor.deleteComment(commentId, memberId);
        return SuccessResponse.of(SuccessCode.SUCCESS, story);
    }
}
