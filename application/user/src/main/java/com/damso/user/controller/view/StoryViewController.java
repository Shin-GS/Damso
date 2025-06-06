package com.damso.user.controller.view;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.pattern.CommonRegexPattern;
import com.damso.common.request.pattern.StoryRegexPattern;
import com.damso.core.enums.story.StoryCommentType;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.response.StoryViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryViewController {
    private final CodeFinder codeFinder;
    private final StoryFinder storyFinder;
    private final StoryEditor storyEditor;
    private final StoryPageFinder storyPageFinder;

    @GetMapping
    public String storyList() {
        return "views/story/list";
    }

    @GetMapping("/{storyId}")
    public String storyView(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        StoryViewResponse storyView = storyFinder.getStoryView(storyId, memberId);
        model.addAttribute("story", storyView);
        model.addAttribute("page", storyPageFinder.getStoryPage(storyId, memberId));
        return "views/story/view";
    }

    @GetMapping("/edit/{storyId}")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        model.addAttribute("storyRegexPattern", CommonRegexPattern.getMap(StoryRegexPattern.class));
        model.addAttribute("commentTypes", codeFinder.getCodes(StoryCommentType.class));
        model.addAttribute("story", storyEditor.resolveTemporaryEditInfo(storyId, memberId));
        return "views/story/edit";
    }
}
