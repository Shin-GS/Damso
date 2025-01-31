package com.damso.userservice.story.impl;

import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.StoryPage;
import com.damso.storage.entity.story.temporary.TemporaryStory;
import com.damso.storage.entity.story.temporary.TemporaryStoryPage;
import com.damso.userservice.story.StoryEditor;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.response.StoryEditPageInfoResponse;
import com.damso.userservice.story.response.StoryEditPageResponse;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import com.damso.userservice.story.response.StoryViewPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public StoryViewPageResponse getStoryPage(Long storyId,
                                              Long memberId) {
        Long firstPageId = storyFinder.getEntity(storyId)
                .getSortedPages()
                .get(0)
                .getId();
        return getStoryPage(storyId, memberId, firstPageId);
    }

    @Override
    public StoryViewPageResponse getStoryPage(Long storyId,
                                              Long memberId,
                                              Long storyPageId) {
        Story story = storyFinder.getEntity(storyId);
        // todo 조회 권한 여부 체크 추가 필요
        if (!story.isPublished()) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

        StoryPage storyPage = story.getStoryPage(storyPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return StoryViewPageResponse.of(storyPage);
    }

    @Override
    public List<StoryViewCommentResponse> getPageComments(Long storyId,
                                                          Long memberId,
                                                          Long storyPageId) {
        Story story = storyFinder.getEntity(storyId);
        // todo 조회 권한 여부 체크 추가 필요
        if (!story.isPublished()) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

        StoryPage storyPage = story.getStoryPage(storyPageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        return new ArrayList<>();
    }
}
