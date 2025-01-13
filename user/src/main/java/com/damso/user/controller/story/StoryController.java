package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryType;
import com.damso.core.request.regex.pattern.CommonRegexPattern;
import com.damso.core.request.regex.pattern.StoryRegexPattern;
import com.damso.user.service.common.CodeFinder;
import com.damso.user.service.story.StoryFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryFinder storyFinder;
    private final CodeFinder codeFinder;

    @GetMapping("/{storyId}/edit")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        model.addAttribute("storyRegexPattern", CommonRegexPattern.getMap(StoryRegexPattern.class));
        model.addAttribute("storyTypes", codeFinder.getCodes(StoryType.class));
        model.addAttribute("commentTypes", codeFinder.getCodes(StoryCommentType.class));
        model.addAttribute("story", storyFinder.getEditInfo(storyId, memberId));
        return "views/story/storyEdit";
    }
}
