package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.request.StoryPageEditRequest;
import com.damso.user.service.story.response.StoryEditPageInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hx/stories/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;

    @GetMapping
    public String getPages(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           Model model) {
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);

        String fragment = " :: page-list";
        return "components/story/edit/pageEdit" + fragment;
    }

    @PostMapping
    public String createPage(@PathVariable("storyId") Long storyId,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.create(storyId, memberId);
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "신규 페이지 추가를 성공했습니다.");

        String fragment = " :: page-edit";
        return "components/story/edit/pageEdit" + fragment;
    }

    @GetMapping("/{storyPageId}")
    public String getPageContent(@PathVariable("storyId") Long storyId,
                                 @PathVariable("storyPageId") Long storyPageId,
                                 @SessionMemberId Long memberId,
                                 Model model) {
        StoryEditPageInfoResponse response = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, storyPageId);
        model.addAttribute("story", response);
        model.addAttribute("files", response.files());

        String fragment = switch (response.storyType()) {
            case TEXT -> " :: text-editor";
            case IMAGE -> " :: image-editor";
            case VIDEO -> " :: video-editor";
        };
        return "components/story/edit/editor" + fragment;
    }

    @PutMapping("/{storyPageId}/type")
    public String updatePageType(@PathVariable("storyId") Long storyId,
                                 @PathVariable("storyPageId") Long storyPageId,
                                 @ModelAttribute @Valid StoryType storyType,
                                 @SessionMemberId Long memberId,
                                 Model model) {
        storyPageEditor.updateType(storyId, memberId, storyPageId, storyType);
        StoryEditPageInfoResponse response = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, storyPageId);
        model.addAttribute("story", response);
        model.addAttribute("files", response.files());

        String fragment = switch (response.storyType()) {
            case TEXT -> " :: text-editor";
            case IMAGE -> " :: image-editor";
            case VIDEO -> " :: video-editor";
        };
        return "components/story/edit/editor" + fragment;
    }

    @PutMapping("/{storyPageId}")
    public String updatePage(@PathVariable("storyId") Long storyId,
                             @PathVariable("storyPageId") Long storyPageId,
                             @RequestBody @Valid StoryPageEditRequest request,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.update(storyId, memberId, storyPageId, request);
        model.addAttribute("message", "페이지 수정에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @DeleteMapping("/{storyPageId}")
    public String deletePage(@PathVariable("storyId") Long storyId,
                             @PathVariable("storyPageId") Long storyPageId,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.delete(storyId, memberId, storyPageId);
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "해당 페이지 삭제를 성공했습니다.");

        String fragment = " :: page-edit";
        return "components/story/edit/pageEdit" + fragment;
    }
}
