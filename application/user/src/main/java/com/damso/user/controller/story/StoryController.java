package com.damso.user.controller.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryCommentType;
import com.damso.common.request.pattern.CommonRegexPattern;
import com.damso.common.request.pattern.StoryRegexPattern;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.story.StoryEditor;
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
    private final CodeFinder codeFinder;
    private final StoryEditor storyEditor;

    @GetMapping("/{storyId}/edit")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        model.addAttribute("storyRegexPattern", CommonRegexPattern.getMap(StoryRegexPattern.class));
        model.addAttribute("commentTypes", codeFinder.getCodes(StoryCommentType.class));
        model.addAttribute("story", storyEditor.resolveTemporaryEditInfo(storyId, memberId));
        return "views/story/edit";
    }
}
