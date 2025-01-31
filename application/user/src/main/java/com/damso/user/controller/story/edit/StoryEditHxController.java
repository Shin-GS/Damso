package com.damso.user.controller.story.edit;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.request.StoryUpdateCommentTypeRequest;
import com.damso.userservice.story.request.StoryUpdateTitleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryEditHxController {
    private final StoryEditor storyEditor;

    @PutMapping("/{storyId}/title")
    public List<ModelAndView> updateTitle(@PathVariable("storyId") Long storyId,
                                          @SessionMemberId Long memberId,
                                          @ModelAttribute @Valid StoryUpdateTitleRequest request) {
        storyEditor.updateTitle(storyId, memberId, request.title());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @PutMapping("/{storyId}/comment-type")
    public List<ModelAndView> updateCommentType(@PathVariable("storyId") Long storyId,
                                                @SessionMemberId Long memberId,
                                                @ModelAttribute @Valid StoryUpdateCommentTypeRequest request) {
        storyEditor.updateCommentType(storyId, memberId, request.commentType());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{storyId}/temporary")
    public List<ModelAndView> reset(@PathVariable("storyId") Long storyId,
                                    @SessionMemberId Long memberId) {
        storyEditor.reset(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 초기화에 성공했습니다.")
                .build();
    }

    @PutMapping("/{storyId}/published")
    public List<ModelAndView> published(@PathVariable("storyId") Long storyId,
                                        @SessionMemberId Long memberId) {
        storyEditor.published(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 반영에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{storyId}")
    public List<ModelAndView> delete(@PathVariable("storyId") Long storyId,
                                     @SessionMemberId Long memberId) {
        storyEditor.delete(storyId, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 삭제에 성공했습니다.")
                .build();
    }
}
