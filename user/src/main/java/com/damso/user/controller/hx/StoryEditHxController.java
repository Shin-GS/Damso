package com.damso.user.controller.hx;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.constant.story.StoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class StoryEditHxController {
    @GetMapping("/stories/{storyId}/edit")
    public String getStory(@PathVariable("storyId") Long storyId,
                           @RequestParam(value = "storyType", required = false) StoryType storyType,
                           @SessionMemberId Long memberId,
                           Model model) {
        // 스토리 권한 체크

        // 스토리 데이터 조회
        List<String> files = new ArrayList<>();
        model.addAttribute("files", new PageImpl<>(files, Pageable.ofSize(3), 3));
        model.addAttribute("storyText", "");

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
