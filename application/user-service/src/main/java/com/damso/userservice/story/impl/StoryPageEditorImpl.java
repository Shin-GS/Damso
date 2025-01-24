package com.damso.userservice.story.impl;

import com.damso.core.enums.story.StoryType;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.temporary.TemporaryStory;
import com.damso.storage.entity.story.temporary.TemporaryStoryPage;
import com.damso.storage.repository.story.StoryPageRepositorySupport;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageEditor;
import com.damso.userservice.story.request.StoryPageEditRequest;
import com.damso.userservice.story.request.StoryPageReorderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
    public Long delete(Long storyId,
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

        TemporaryStoryPage temporaryStoryPageToMove = temporaryStory.getStoryPageByOrder(temporaryStoryPage.getPageOrder());
        return ObjectUtils.isEmpty(temporaryStoryPageToMove) ? null : temporaryStoryPageToMove.getId();
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
