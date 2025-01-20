package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.StoryPageFinder;
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
        storyPageEditor.createPage(storyId, memberId);
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "신규 페이지 추가를 성공했습니다.");

        String fragment = " :: page-edit";
        return "components/story/edit/pageEdit" + fragment;
    }

    @DeleteMapping("/{storyPageId}")
    public String deletePage(@PathVariable("storyId") Long storyId,
                             @PathVariable("storyPageId") Long storyPageId,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.deletePage(storyId, memberId, storyPageId);
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "해당 페이지 삭제를 성공했습니다.");

        String fragment = " :: page-edit";
        return "components/story/edit/pageEdit" + fragment;
    }
}
