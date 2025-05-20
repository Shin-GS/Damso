package com.damso.user.controller.hx.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.user.controller.api.story.StoryCommentApi;
import com.damso.user.controller.hx.reqeust.StoryCommentCreateHxRequest;
import com.damso.userservice.story.request.StoryCommentUpdateRequest;
import com.damso.userservice.story.response.StoryCommentEditResponse;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryCommentHxController {
    private final StoryCommentApi storyCommentApi;

    @PostMapping("/pages/comments")
    public List<ModelAndView> createComment(@ModelAttribute @Valid StoryCommentCreateHxRequest request,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentApi.createComment(
                        request.storyId(),
                        request.storyPageId(),
                        StoryCommentUpdateRequest.of(request.text()),
                        memberId)
                .getResult();

        List<StoryViewCommentResponse> comments = storyCommentApi.findComments(
                        story.storyId(),
                        story.storyPageId(),
                        memberId,
                        PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getResult();

        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html",
                        "components/story/view/comment :: comment-list",
                        Map.of("comments", comments))
                .build();
    }

    @PutMapping("/pages/comments/{commentId}")
    public List<ModelAndView> updateComment(@PathVariable("commentId") Long commentId,
                                            @ModelAttribute @Valid StoryCommentUpdateRequest request,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentApi.updateComment(commentId, request, memberId).getResult();

        List<StoryViewCommentResponse> comments = storyCommentApi.findComments(
                        story.storyId(),
                        story.storyPageId(),
                        memberId,
                        PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getResult();

        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html",
                        "components/story/view/comment :: comment-list",
                        Map.of("comments", comments))
                .build();
    }

    @DeleteMapping("/pages/comments/{commentId}")
    public List<ModelAndView> deleteComment(@PathVariable("commentId") Long commentId,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse story = storyCommentApi.deleteComment(commentId, memberId).getResult();

        List<StoryViewCommentResponse> comments = storyCommentApi.findComments(
                        story.storyId(),
                        story.storyPageId(),
                        memberId,
                        PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt")))
                .getResult();

        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html",
                        "components/story/view/comment :: comment-list",
                        Map.of("comments", comments))
                .build();
    }
}
