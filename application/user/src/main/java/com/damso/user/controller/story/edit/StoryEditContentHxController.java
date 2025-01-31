package com.damso.user.controller.story.edit;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StoryPageEditRequest;
import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import jakarta.validation.Valid;
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
public class StoryEditContentHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;
    private final CodeFinder codeFinder;

    @GetMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> getPageContent(@PathVariable("storyId") Long storyId,
                                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                             @SessionMemberId Long memberId) {
        StoryEditPageInfoResponse storyPageInfo = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageId);

        Map<String, Object> contentData = new HashMap<>();
        contentData.put("storyTypes", codeFinder.getCodes(StoryType.class));
        contentData.put("story", storyPageInfo);
        contentData.put("files", storyPageInfo.files());

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        pageData.put("storyId", storyId);
        pageData.put("currentPageId", temporaryStoryPageId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html", "components/story/edit/content :: content", contentData)
                .addFragment("templates/components/story/edit/pageList.html", "components/story/edit/pageList :: page-list", pageData)
                .build();
    }

    @GetMapping("/first-page")
    public List<ModelAndView> getFirstPageContent(@PathVariable("storyId") Long storyId,
                                                  @SessionMemberId Long memberId) {
        StoryEditPageInfoResponse storyPageInfo = storyPageFinder.getFirstTemporaryStoryPageInfo(storyId, memberId);

        Map<String, Object> contentData = new HashMap<>();
        contentData.put("storyTypes", codeFinder.getCodes(StoryType.class));
        contentData.put("story", storyPageInfo);
        contentData.put("files", storyPageInfo.files());
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html", "components/story/edit/content :: content", contentData)
                .build();
    }

    @PutMapping("/{temporaryStoryPageId}/type")
    public List<ModelAndView> updatePageType(@PathVariable("storyId") Long storyId,
                                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                             @ModelAttribute @Valid StoryType storyType,
                                             @SessionMemberId Long memberId) {
        storyPageEditor.updateType(storyId, memberId, temporaryStoryPageId, storyType);

        StoryEditPageInfoResponse storyPageInfo = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageId);
        Map<String, Object> contentData = new HashMap<>();
        contentData.put("storyTypes", codeFinder.getCodes(StoryType.class));
        contentData.put("story", storyPageInfo);
        contentData.put("files", storyPageInfo.files());

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        pageData.put("storyId", storyId);
        pageData.put("currentPageId", temporaryStoryPageId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html", "components/story/edit/content :: content", contentData)
                .addFragment("templates/components/story/edit/pageList.html", "components/story/edit/pageList :: page-list", pageData)
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @PutMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> updatePage(@PathVariable("storyId") Long storyId,
                                         @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                         @RequestBody @Valid StoryPageEditRequest request,
                                         @SessionMemberId Long memberId) {
        storyPageEditor.update(storyId, memberId, temporaryStoryPageId, request);

        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html", "components/toast :: success", "message", "스토리 임시저장에 성공했습니다.")
                .build();
    }
}
