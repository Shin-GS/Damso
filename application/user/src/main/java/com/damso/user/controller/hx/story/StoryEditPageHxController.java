package com.damso.user.controller.hx.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.core.enums.story.StoryType;
import com.damso.user.controller.api.story.StoryEditPageApi;
import com.damso.userservice.common.CodeFinder;
import com.damso.userservice.common.response.CodeResponse;
import com.damso.userservice.story.request.StoryPageEditRequest;
import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/edit/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageHxController {
    private final StoryEditPageApi storyEditPageApi;
    private final CodeFinder codeFinder;

    @GetMapping
    public List<ModelAndView> getPages(@PathVariable("storyId") Long storyId,
                                       @SessionMemberId Long memberId) {
        List<StoryEditPageResponse> storyPages = storyEditPageApi.getPages(storyId, memberId).getResult();
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html",
                        "components/story/edit/pageList :: page-list",
                        Map.of("storyPages", storyPages,
                                "storyId", storyId,
                                "currentPageId", storyPages.get(0).id()))
                .build();
    }

    @PostMapping
    public List<ModelAndView> createPage(@PathVariable("storyId") Long storyId,
                                         @SessionMemberId Long memberId) {
        storyEditPageApi.createPage(storyId, memberId);

        List<StoryEditPageResponse> storyPages = storyEditPageApi.getPages(storyId, memberId).getResult();
        Long lastTemporaryStoryPageId = storyPages.get(storyPages.size() - 1).id(); // last page
        StoryEditPageInfoResponse pageContent = storyEditPageApi.getPageContent(storyId, lastTemporaryStoryPageId, memberId).getResult();
        List<CodeResponse> codes = codeFinder.getCodes(StoryType.class);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html",
                        "components/story/edit/pageList :: page-list",
                        Map.of("storyPages", storyPages,
                                "storyId", storyId,
                                "currentPageId", lastTemporaryStoryPageId))
                .addFragment("templates/components/story/edit/content.html",
                        "components/story/edit/content :: content",
                        Map.of("storyTypes", codes,
                                "story", pageContent,
                                "files", pageContent.files()))
                .addFragment("templates/components/toast.html",
                        "components/toast :: success",
                        "message",
                        "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @DeleteMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> deletePage(@PathVariable("storyId") Long storyId,
                                         @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                         @SessionMemberId Long memberId) {
        Long temporaryStoryPageIdToMove = storyEditPageApi.deletePage(storyId, temporaryStoryPageId, memberId).getResult();

        List<StoryEditPageResponse> storyPages = storyEditPageApi.getPages(storyId, memberId).getResult();
        StoryEditPageInfoResponse pageContent = storyEditPageApi.getPageContent(storyId, temporaryStoryPageIdToMove, memberId).getResult();
        List<CodeResponse> codes = codeFinder.getCodes(StoryType.class);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/pageList.html",
                        "components/story/edit/pageList :: page-list",
                        Map.of("storyPages", storyPages,
                                "storyId", storyId,
                                "currentPageId", temporaryStoryPageIdToMove))
                .addFragment("templates/components/story/edit/content.html",
                        "components/story/edit/content :: content",
                        Map.of("storyTypes", codes,
                                "story", pageContent,
                                "files", pageContent.files()))
                .addFragment("templates/components/toast.html",
                        "components/toast :: success",
                        "message",
                        "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @GetMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> getPageContent(@PathVariable("storyId") Long storyId,
                                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                             @SessionMemberId Long memberId) {
        StoryEditPageInfoResponse pageContent = storyEditPageApi.getPageContent(storyId, temporaryStoryPageId, memberId).getResult();

        List<StoryEditPageResponse> storyPages = storyEditPageApi.getPages(storyId, memberId).getResult();
        List<CodeResponse> codes = codeFinder.getCodes(StoryType.class);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html",
                        "components/story/edit/content :: content",
                        Map.of("storyTypes", codes,
                                "story", pageContent,
                                "files", pageContent.files()))
                .addFragment("templates/components/story/edit/pageList.html",
                        "components/story/edit/pageList :: page-list",
                        Map.of("storyPages", storyPages,
                                "storyId", storyId,
                                "currentPageId", temporaryStoryPageId))
                .build();
    }

    @GetMapping("/first-page")
    public List<ModelAndView> getFirstPageContent(@PathVariable("storyId") Long storyId,
                                                  @SessionMemberId Long memberId) {
        StoryEditPageInfoResponse pageContent = storyEditPageApi.getFirstPageContent(storyId, memberId).getResult();
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html",
                        "components/story/edit/content :: content",
                        Map.of("storyTypes", codeFinder.getCodes(StoryType.class),
                                "story", pageContent,
                                "files", pageContent.files()))
                .build();
    }

    @PutMapping("/{temporaryStoryPageId}/type")
    public List<ModelAndView> updatePageType(@PathVariable("storyId") Long storyId,
                                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                             @ModelAttribute @Valid StoryType storyType,
                                             @SessionMemberId Long memberId) {
        storyEditPageApi.updatePageType(storyId, temporaryStoryPageId, storyType, memberId);

        StoryEditPageInfoResponse pageContent = storyEditPageApi.getPageContent(storyId, temporaryStoryPageId, memberId).getResult();
        List<StoryEditPageResponse> storyPages = storyEditPageApi.getPages(storyId, memberId).getResult();
        List<CodeResponse> codes = codeFinder.getCodes(StoryType.class);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/edit/content.html",
                        "components/story/edit/content :: content",
                        Map.of("storyTypes", codes,
                                "story", pageContent,
                                "files", pageContent.files()))
                .addFragment("templates/components/story/edit/pageList.html",
                        "components/story/edit/pageList :: page-list",
                        Map.of("storyPages", storyPages,
                                "storyId", storyId,
                                "currentPageId", temporaryStoryPageId))
                .addFragment("templates/components/toast.html",
                        "components/toast :: success",
                        "message",
                        "스토리 임시저장에 성공했습니다.")
                .build();
    }

    @PutMapping("/{temporaryStoryPageId}")
    public List<ModelAndView> updatePage(@PathVariable("storyId") Long storyId,
                                         @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                         @RequestBody @Valid StoryPageEditRequest request,
                                         @SessionMemberId Long memberId) {
        storyEditPageApi.updatePage(storyId, temporaryStoryPageId, request, memberId);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/toast.html",
                        "components/toast :: success",
                        "message",
                        "스토리 임시저장에 성공했습니다.")
                .build();
    }
}
