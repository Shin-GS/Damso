package com.damso.user.controller.hx.story;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.user.controller.api.story.StoryCommentApi;
import com.damso.user.controller.api.story.StoryFindApi;
import com.damso.userservice.story.request.StorySearchRequest;
import com.damso.userservice.story.response.StorySearchResponse;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import com.damso.userservice.story.response.StoryViewPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories")
@RequiredArgsConstructor
public class StoryFindHxController {
    private final StoryFindApi storyFindApi;
    private final StoryCommentApi storyCommentApi;

    @GetMapping
    public List<ModelAndView> storyList(StorySearchRequest request) {
        List<StorySearchResponse> stories = storyFindApi.storyList(request).getResult();
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/list/cardList.html",
                        "components/story/list/cardList :: story-card-List",
                        Map.of("stories", stories, "nextPage", request.getPage() + 1))
                .build();
    }

    @GetMapping("/{storyId}/pages/first-page")
    public List<ModelAndView> storyViewFirstPage(@PathVariable("storyId") Long storyId,
                                                 @SessionMemberId Long memberId,
                                                 @PageableDefault(size = 5) Pageable pageable) {
        StoryViewPageResponse firstPage = storyFindApi.getFirstStoryPage(storyId, memberId).getResult();
        return storyViewPage(storyId, firstPage.id(), memberId, pageable);
    }

    @GetMapping("/{storyId}/pages/{pageId}")
    public List<ModelAndView> storyViewPage(@PathVariable("storyId") Long storyId,
                                            @PathVariable("pageId") Long pageId,
                                            @SessionMemberId Long memberId,
                                            @PageableDefault(size = 5) Pageable pageable) {
        StoryViewPageResponse story = storyFindApi.getStoryPage(storyId, pageId, memberId).getResult();
        List<StoryViewCommentResponse> comments = storyCommentApi.findComments(storyId, pageId, memberId, pageable).getResult();
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/pageContent.html",
                        "components/story/view/pageContent :: story-container",
                        Map.of("page", story))
                .addFragment("templates/components/story/view/comment.html",
                        "components/story/view/comment :: comment-list",
                        Map.of("comments", comments))
                .build();
    }
}
