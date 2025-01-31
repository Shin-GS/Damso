package com.damso.user.controller.story.edit;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.core.enums.story.StoryType;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/edit/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;
    private final CodeFinder codeFinder;

    @GetMapping
    public List<ModelAndView> getPages(@PathVariable("storyId") Long storyId,
                                       @SessionMemberId Long memberId) {
        List<StoryEditPageResponse> storyPages = storyPageFinder.getTemporaryStoryPages(storyId, memberId);

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("storyPages", storyPages);
        pageData.put("storyId", storyId);
        pageData.put("currentPageId", storyPages.get(0).id()); // first page
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html", "components/story/edit/pageList :: page-list", pageData)
                .build();
    }

    @PostMapping
    public List<ModelAndView> createPage(@PathVariable("storyId") Long storyId,
                                         @SessionMemberId Long memberId) {
        storyPageEditor.create(storyId, memberId);

        List<StoryEditPageResponse> storyPages = storyPageFinder.getTemporaryStoryPages(storyId, memberId);
        Long lastTemporaryStoryPageId = storyPages.get(storyPages.size() - 1).id(); // last page
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("storyPages", storyPages);
        pageData.put("storyId", storyId);
        pageData.put("currentPageId", lastTemporaryStoryPageId);

        StoryEditPageInfoResponse response = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, lastTemporaryStoryPageId);
        Map<String, Object> contentData = new HashMap<>();
        contentData.put("storyTypes", codeFinder.getCodes(StoryType.class));
        contentData.put("story", response);
        contentData.put("files", response.files());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html", "components/story/edit/pageList :: page-list", pageData)
                .addFragment("templates/components/story/edit/content.html", "components/story/edit/content :: content", contentData)
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> deletePage(@PathVariable("storyId") Long storyId,
                                         @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                         @SessionMemberId Long memberId) {
        Long temporaryStoryPageIdToMove = storyPageEditor.delete(storyId, memberId, temporaryStoryPageId);

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        pageData.put("storyId", storyId);
        pageData.put("currentPageId", temporaryStoryPageIdToMove);

        StoryEditPageInfoResponse storyPageInfo = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageIdToMove);
        Map<String, Object> contentData = new HashMap<>();
        contentData.put("storyTypes", codeFinder.getCodes(StoryType.class));
        contentData.put("story", storyPageInfo);
        contentData.put("files", storyPageInfo.files());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html", "components/story/edit/pageList :: page-list", pageData)
                .addFragment("templates/components/story/edit/content.html", "components/story/edit/content :: content", contentData)
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }
}
