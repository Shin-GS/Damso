package com.damso.user.controller.view;

import com.damso.core.constant.story.StoryCommentType;
import com.damso.core.constant.story.StoryType;
import com.damso.user.service.story.model.EditStoryModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class StoryController {
    @GetMapping("/stories/{storyId}/edit")
    public String storyEdit(@PathVariable("storyId") Long storyId,
                            Model model) {
        model.addAttribute("story", new EditStoryModel(1L, "테스트", StoryType.TEXT, StoryCommentType.ALL, true));
        model.addAttribute("files", new PageImpl<>(new ArrayList<>(), Pageable.ofSize(3), 3));
        model.addAttribute("storyText", "");
        return "fragments/story/storyEdit";
    }
}
