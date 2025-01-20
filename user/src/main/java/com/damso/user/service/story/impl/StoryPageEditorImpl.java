package com.damso.user.service.story.impl;

import com.damso.core.enums.story.StoryType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;
import com.damso.domain.db.repository.story.StoryPageRepositorySupport;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.request.StoryPageEditRequest;
import com.damso.user.service.story.request.StoryPageReorderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryPageEditorImpl implements StoryPageEditor {
    private final StoryFinder storyFinder;
    private final StoryEditor storyEditor;
    private final StoryPageRepositorySupport storyPageRepositorySupport;

    @Override
    public void create(Long storyId,
                       Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        if (storyPageRepositorySupport.countByNotDeleted(story) > 10) {
            throw new BusinessException(ErrorCode.STORY_PAGE_MAX_EXCEED);
        }

        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        temporaryStory.addPage();
    }

    @Override
    public void delete(Long storyId,
                       Long memberId,
                       Long temporaryStoryPageId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        if (storyPageRepositorySupport.countByNotDeleted(story) <= 1) {
            throw new BusinessException(ErrorCode.STORY_PAGE_MIN_EXCEED);
        }

        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        TemporaryStoryPage temporaryStoryPage = temporaryStory.getStoryPage(temporaryStoryPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        temporaryStory.deletePage(temporaryStoryPage);
    }

    @Override
    public void reorder(Long storyId,
                        Long memberId,
                        List<StoryPageReorderRequest.StoryPageOrderRequest> pageOrders) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        pageOrders.stream()
                .sorted(Comparator.comparingInt(StoryPageReorderRequest.StoryPageOrderRequest::order))
                .forEach(pageOrder -> temporaryStory.getStoryPage(pageOrder.storyPageId())
                        .ifPresent(temporaryStoryPage -> temporaryStoryPage.setPageOrder(pageOrder.order()))
                );
        temporaryStory.reorderPages();
    }

    @Override
    public void updateType(Long storyId,
                           Long memberId,
                           Long temporaryStoryPageId,
                           StoryType storyType) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        TemporaryStoryPage temporaryStoryPage = temporaryStory.getStoryPage(temporaryStoryPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        temporaryStoryPage.setStoryType(storyType);
        temporaryStoryPage.update("",
                "",
                new ArrayList<>());
    }

    @Override
    public void update(Long storyId,
                       Long memberId,
                       Long temporaryStoryPageId,
                       StoryPageEditRequest request) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = storyEditor.resolveTemporaryStory(story);
        TemporaryStoryPage temporaryStoryPage = temporaryStory.getStoryPage(temporaryStoryPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        temporaryStoryPage.update(request.text(),
                request.planText(),
                request.files());
    }
}
