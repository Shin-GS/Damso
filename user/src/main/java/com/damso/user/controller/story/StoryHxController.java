package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.model.StoryEditInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class StoryHxController {
    private final StoryFinder storyFinder;

    @GetMapping("/stories/{storyId}/edit")
    public String getStory(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           @RequestParam(value = "storyType", required = false) StoryType storyType,
                           Model model) {
        StoryEditInfoModel editInfoModel = storyFinder.getEditInfo(storyId, memberId);
        model.addAttribute("story", editInfoModel);

        if (storyType == null) {
            return "components/storyEditor :: text-editor";
        }

        return switch (storyType) {
            case TEXT -> "components/storyEditor :: text-editor";
            case IMAGE -> "components/storyEditor :: image-editor";
            case VIDEO -> "components/storyEditor :: video-editor";
        };
    }
}
