package com.damso.user.controller.story;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.story.StoryType;
import com.damso.core.response.hx.HxPostMapping;
import com.damso.core.response.hx.response.FragmentResponse;
import com.damso.core.response.hx.response.FragmentResponseBuilder;
import com.damso.core.response.hx.response.FragmentResponseRenderer;
import com.damso.user.service.common.CodeFinder;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.request.StoryPageEditRequest;
import com.damso.user.service.story.response.StoryEditPageInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/{storyId}/pages")
@RequiredArgsConstructor
public class StoryEditPageHxController {
    private final StoryPageFinder storyPageFinder;
    private final StoryPageEditor storyPageEditor;
    private final CodeFinder codeFinder;
    private final FragmentResponseRenderer fragmentResponseRenderer;

    @GetMapping
    public String getPages(@PathVariable("storyId") Long storyId,
                           @SessionMemberId Long memberId,
                           Model model) {
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);

        String fragment = " :: page-list";
        return "components/story/edit/pageEdit" + fragment;
    }

    @PostMapping
    public String createPage(@PathVariable("storyId") Long storyId,
                             @SessionMemberId Long memberId) {
        storyPageEditor.create(storyId, memberId);

        Map<String, Object> pageListData = new HashMap<>();
        pageListData.put("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        pageListData.put("storyId", storyId);

        List<FragmentResponse> fragmentResponses = new FragmentResponseBuilder()
                .addFragment("templates/components/toast.html", "success", "message", "신규 페이지 추가를 성공했습니다.")
//                .addTemplate("components/toast", Map.of("message", "신규 페이지 추가를 성공했습니다."))
                .build();

        return fragmentResponses.stream()
                .findFirst()
                .map(fragmentResponseRenderer::renderFragment)
                .orElse("Error rendering fragment");
    }

    @GetMapping("/{temporaryStoryPageId}")
    public String getPageContent(@PathVariable("storyId") Long storyId,
                                 @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                 @SessionMemberId Long memberId,
                                 Model model) {
        StoryEditPageInfoResponse response = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageId);
        model.addAttribute("storyTypes", codeFinder.getCodes(StoryType.class));
        model.addAttribute("story", response);
        model.addAttribute("files", response.files());

        String fragment = " :: content";
        return "components/story/edit/contentEdit" + fragment;
    }

    @GetMapping("/first-page")
    public String getFirstPageContent(@PathVariable("storyId") Long storyId,
                                      @SessionMemberId Long memberId,
                                      Model model) {
        StoryEditPageInfoResponse response = storyPageFinder.getFirstTemporaryStoryPageInfo(storyId, memberId);
        model.addAttribute("storyTypes", codeFinder.getCodes(StoryType.class));
        model.addAttribute("story", response);
        model.addAttribute("files", response.files());

        String fragment = " :: content";
        return "components/story/edit/contentEdit" + fragment;
    }

    @PutMapping("/{temporaryStoryPageId}/type")
    public String updatePageType(@PathVariable("storyId") Long storyId,
                                 @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                                 @ModelAttribute @Valid StoryType storyType,
                                 @SessionMemberId Long memberId,
                                 Model model) {
        storyPageEditor.updateType(storyId, memberId, temporaryStoryPageId, storyType);
        StoryEditPageInfoResponse response = storyPageFinder.getTemporaryStoryPageInfo(storyId, memberId, temporaryStoryPageId);
        model.addAttribute("storyTypes", codeFinder.getCodes(StoryType.class));
        model.addAttribute("story", response);
        model.addAttribute("files", response.files());
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "타입 변경에 성공했습니다.");

        String fragment = " :: content-type";
        return "components/story/edit/contentEdit" + fragment;
    }

    @PutMapping("/{temporaryStoryPageId}")
    public String updatePage(@PathVariable("storyId") Long storyId,
                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                             @RequestBody @Valid StoryPageEditRequest request,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.update(storyId, memberId, temporaryStoryPageId, request);
        model.addAttribute("message", "페이지 수정에 성공했습니다.");

        String fragment = " :: success";
        return "components/toast" + fragment;
    }

    @DeleteMapping("/{temporaryStoryPageId}")
    public String deletePage(@PathVariable("storyId") Long storyId,
                             @PathVariable("temporaryStoryPageId") Long temporaryStoryPageId,
                             @SessionMemberId Long memberId,
                             Model model) {
        storyPageEditor.delete(storyId, memberId, temporaryStoryPageId);
        model.addAttribute("storyPages", storyPageFinder.getTemporaryStoryPages(storyId, memberId));
        model.addAttribute("storyId", storyId);
        model.addAttribute("message", "해당 페이지 삭제를 성공했습니다.");

        String fragment = " :: page-edit";
        return "components/story/edit/pageEdit" + fragment;
    }
}
