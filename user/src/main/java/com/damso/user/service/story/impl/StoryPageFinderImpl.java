package com.damso.user.service.story.impl;

import com.damso.domain.db.entity.story.Story;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.response.StoryEditPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryPageFinderImpl implements StoryPageFinder {
    private final StoryFinder storyFinder;

    @Override
    public List<StoryEditPageResponse> getPages(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        return story.getStoryPages().stream()
                .filter(page -> !page.isDeleted())
                .map(StoryEditPageResponse::of)
                .toList();
    }
}
