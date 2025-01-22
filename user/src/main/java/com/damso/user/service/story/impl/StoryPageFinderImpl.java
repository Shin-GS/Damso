package com.damso.user.service.story.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.StoryPageFinder;
import com.damso.user.service.story.response.StoryEditPageInfoResponse;
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
    public List<StoryEditPageResponse> getTemporaryStoryPages(Long storyId,
                                                              Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        return temporaryStory.getEditableTemporaryStoryPages().stream()
                .map(StoryEditPageResponse::of)
                .toList();
    }

    @Override
    public StoryEditPageInfoResponse getFirstTemporaryStoryPageInfo(Long storyId,
                                                                    Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        TemporaryStoryPage temporaryStoryPage = temporaryStory.getEditableTemporaryStoryPages().stream()
                .min(Comparator.comparing(TemporaryStoryPage::getPageOrder))
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return StoryEditPageInfoResponse.of(storyId, temporaryStoryPage);
    }

    @Override
    @Transactional
    public StoryEditPageInfoResponse getTemporaryStoryPageInfo(Long storyId,
                                                               Long memberId,
                                                               Long temporaryStoryPageId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        TemporaryStoryPage temporaryStoryPage = temporaryStory.getStoryPage(temporaryStoryPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return StoryEditPageInfoResponse.of(storyId, temporaryStoryPage);
    }
}
