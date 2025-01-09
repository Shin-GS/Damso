package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
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

    @GetMapping("/stories/{storyId}/edit")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            @SessionMemberId Long memberId,
                            Model model) {
        StoryEditInfoModel editInfoModel = storyFinder.getEditInfo(storyId, memberId);
        model.addAttribute("story", editInfoModel);
        return "views/story/storyEdit";
    }
}
