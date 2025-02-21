package com.damso.user.controller.story.view;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.story.StoryCommentEditor;
import com.damso.userservice.story.StoryCommentFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StoryCommentCreateRequest;
import com.damso.userservice.story.request.StoryCommentUpdateRequest;
import com.damso.userservice.story.response.StoryCommentEditResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryViewHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryCommentFinder storyCommentFinder;
    private final StoryCommentEditor storyCommentEditor;

    @GetMapping("/{storyId}/pages/{pageId}")
    public List<ModelAndView> storyViewPage(@PathVariable("storyId") Long storyId,
                                            @PathVariable("pageId") Long pageId,
                                            @SessionMemberId Long memberId,
                                            @PageableDefault(size = 30) Pageable pageable) {
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("page", storyPageFinder.getStoryPage(storyId, pageId, memberId));

        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comments", storyCommentFinder.findComments(storyId, pageId, memberId, pageable));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/pageContent.html", "components/story/view/pageContent :: story-container", pageData)
                .addFragment("templates/components/story/view/comment.html", "components/story/view/comment :: comment-list", commentData)
                .build();
    }

    @PostMapping("/pages/comments")
    public List<ModelAndView> createComment(@ModelAttribute @Valid StoryCommentCreateRequest request,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse response = storyCommentEditor.createComment(request, memberId);

        Pageable pageable = PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comments", storyCommentFinder.findComments(response.storyId(), response.storyPageId(), memberId, pageable));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html", "components/story/view/comment :: comment-list", commentData)
                .build();
    }

    @PutMapping("/pages/comments/{commentId}")
    public List<ModelAndView> updateComment(@PathVariable("commentId") Long commentId,
                                            @ModelAttribute @Valid StoryCommentUpdateRequest request,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse response = storyCommentEditor.updateComment(commentId, request, memberId);

        Pageable pageable = PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comments", storyCommentFinder.findComments(response.storyId(), response.storyPageId(), memberId, pageable));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html", "components/story/view/comment :: comment-list", commentData)
                .build();
    }

    @DeleteMapping("/pages/comments/{commentId}")
    public List<ModelAndView> deleteComment(@PathVariable("commentId") Long commentId,
                                            @SessionMemberId Long memberId) {
        StoryCommentEditResponse response = storyCommentEditor.deleteComment(commentId, memberId);

        Pageable pageable = PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comments", storyCommentFinder.findComments(response.storyId(), response.storyPageId(), memberId, pageable));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/comment.html", "components/story/view/comment :: comment-list", commentData)
                .build();
    }
}
