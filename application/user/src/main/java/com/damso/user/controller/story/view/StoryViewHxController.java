package com.damso.user.controller.story.view;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.story.StoryPageFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx/stories/{storyId}")
@RequiredArgsConstructor
public class StoryViewHxController {
    private final StoryPageFinder storyPageFinder;

    @GetMapping("/pages/{pageId}")
    public List<ModelAndView> storyViewPage(@PathVariable("storyId") Long storyId,
                                            @PathVariable("pageId") Long pageId,
                                            @SessionMemberId Long memberId) {
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("page", storyPageFinder.getStoryPage(storyId, memberId, pageId));

        Map<String, Object> commentData = new HashMap<>();
        commentData.put("comments", storyPageFinder.getPageComments(storyId, memberId, pageId));
        return new ModelAndViewBuilder()
                .addFragment("templates/components/story/view/pageContent.html", "components/story/view/pageContent :: story-container", pageData)
                .addFragment("templates/components/story/view/comment.html", "components/story/view/comment :: comments-container", commentData)
                .build();
    }
}
