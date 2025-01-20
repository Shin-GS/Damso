package com.damso.user.service.story.impl;

import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.response.StoryEditPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryPageFinderImpl implements StoryPageFinder {
    private final StoryFinder storyFinder;
    private final StoryEditor storyEditor;

    @Override
    @Transactional
    public List<StoryEditPageResponse> getTemporaryStoryPages(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        return temporaryStory.getTemporaryStoryPages().stream()
                .filter(page -> !page.isDeleted())
                .sorted(Comparator.comparing(TemporaryStoryPage::getPageOrder))
                .map(StoryEditPageResponse::of)
                .toList();
    }
}
