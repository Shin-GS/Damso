package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.ModelAndViewBuilder;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.response.StoryEditPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;

    @GetMapping
    public List<ModelAndView> getPages(@PathVariable("storyId") Long storyId,
                                       @SessionMemberId Long memberId) {
        List<StoryEditPageResponse> temporaryStoryPages = storyPageFinder.getTemporaryStoryPages(storyId, memberId);

        Map<String, Object> pageListData = new HashMap<>();
        pageListData.put("storyPages", temporaryStoryPages);
        pageListData.put("storyId", storyId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageEdit.html", "components/story/edit/pageEdit :: page-list", pageListData)
                .build();
    }

    @PostMapping
    public List<ModelAndView> createPage(@PathVariable("storyId") Long storyId,
                                         @SessionMemberId Long memberId) {
        storyPageEditor.create(storyId, memberId);
        List<StoryEditPageResponse> temporaryStoryPages = storyPageFinder.getTemporaryStoryPages(storyId, memberId);

        Map<String, Object> pageListData = new HashMap<>();
        pageListData.put("storyPages", temporaryStoryPages);
        pageListData.put("storyId", storyId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageEdit.html", "components/story/edit/pageEdit :: page-list", pageListData)
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> deletePage(@PathVariable("storyId") Long storyId,
                                         @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                         @SessionMemberId Long memberId) {
        storyPageEditor.delete(storyId, memberId, temporaryStoryPageId);
        List<StoryEditPageResponse> temporaryStoryPages = storyPageFinder.getTemporaryStoryPages(storyId, memberId);

        Map<String, Object> pageListData = new HashMap<>();
        pageListData.put("storyPages", temporaryStoryPages);
        pageListData.put("storyId", storyId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageEdit.html", "components/story/edit/pageEdit :: page-list", pageListData)
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }
}
