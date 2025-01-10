package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.common.CodeFinder;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.model.StoryEditInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class StoryController {
    private final StoryFinder storyFinder;
    private final CodeFinder codeFinder;

    @GetMapping("/stories/{storyId}/edit")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        StoryEditInfoModel editInfoModel = storyFinder.getEditInfo(storyId, memberId);
        model.addAttribute("story", editInfoModel);
        model.addAttribute("storyTypes", codeFinder.getCodes(StoryType.class));
        model.addAttribute("commentTypes", codeFinder.getCodes(StoryCommentType.class));
        return "views/story/storyEdit";
    }
}
