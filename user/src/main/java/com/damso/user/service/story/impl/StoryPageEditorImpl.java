package com.damso.user.service.story.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.StoryPage;
import com.damso.domain.db.repository.story.StoryPageRepositorySupport;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.StoryPageEditor;
import com.damso.user.service.story.request.StoryPageReorderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryPageEditorImpl implements StoryPageEditor {
    private final StoryFinder storyFinder;
    private final StoryPageRepositorySupport storyPageRepositorySupport;

    @Override
    public void createPage(Long storyId,
                           Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        if (storyPageRepositorySupport.countByNotDeleted(story) >= 10) {
            throw new BusinessException(ErrorCode.STORY_PAGE_MAX_EXCEED);
        }

        story.addPage();
    }

    @Override
    public void deletePage(Long storyId,
                           Long memberId,
                           Long storyPageId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        if (storyPageRepositorySupport.countByNotDeleted(story) <= 1) {
            throw new BusinessException(ErrorCode.STORY_PAGE_MIN_EXCEED);
        }

        StoryPage storyPage = story.getStoryPage(storyPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        story.deletePage(storyPage);
    }

    @Override
    public void reorderPage(Long storyId,
                            Long memberId,
                            List<StoryPageReorderRequest.StoryPageOrderRequest> pageOrders) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        pageOrders.stream()
                .sorted(Comparator.comparingInt(StoryPageReorderRequest.StoryPageOrderRequest::order))
                .forEach(pageOrder -> story.getStoryPage(pageOrder.storyPageId())
                        .ifPresent(storyPage -> storyPage.setPageOrder(pageOrder.order()))
                );

        story.reorderPages();
    }
}
