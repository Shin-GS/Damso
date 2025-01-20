package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.request.StoryUpdateCommentTypeRequest;
import com.damso.user.service.story.request.StoryUpdateTitleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryEditHxController {
    private final StoryEditor storyEditor;

//    @GetMapping("/{storyId}/edit")
//    public String getStory(@PathVariable("storyId") Long storyId,
//                           @SessionMemberId Long memberId,
//                           @RequestParam(value = "storyType", required = false, defaultValue = "TEXT") StoryType storyType,
//                           Model model) {
//        model.addAttribute("story", storyEditor.resolveTemporaryEditInfo(storyId, memberId));
////        model.addAttribute("images", editInfoResponse.images());
////        model.addAttribute("video", editInfoResponse.video());
//
//        String fragment = switch (storyType) {
//            case TEXT -> " :: text-editor";
//            case IMAGE -> " :: image-editor";
//            case VIDEO -> " :: video-editor";
//        };
//        return "components/story/edit/editor" + fragment;
//    }

    @PutMapping("/{storyId}/title")
    public String updateTitle(@PathVariable("storyId") Long storyId,
                              @SessionMemberId Long memberId,
                              @ModelAttribute @Valid StoryUpdateTitleRequest request,
                              Model model) {
        storyEditor.updateTitle(storyId, memberId, request.title());
        model.addAttribute("message", "스토리 임시저장에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @PutMapping("/{storyId}/comment-type")
    public String updateCommentType(@PathVariable("storyId") Long storyId,
                                    @SessionMemberId Long memberId,
                                    @ModelAttribute @Valid StoryUpdateCommentTypeRequest request,
                                    Model model) {
        storyEditor.updateCommentType(storyId, memberId, request.commentType());
        model.addAttribute("message", "스토리 임시저장에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @DeleteMapping("/{storyId}/temporary")
    public String reset(@PathVariable("storyId") Long storyId,
                        @SessionMemberId Long memberId,
                        Model model) {
        storyEditor.reset(storyId, memberId);
        model.addAttribute("message", "스토리 초기화에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @PutMapping("/{storyId}/published")
    public String published(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        storyEditor.published(storyId, memberId);
        model.addAttribute("message", "스토리 반영에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @DeleteMapping("/{storyId}")
    public String delete(@PathVariable("storyId") Long storyId,
                         @SessionMemberId Long memberId,
                         Model model) {
        storyEditor.delete(storyId, memberId);
        model.addAttribute("message", "스토리 삭제에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }
}
