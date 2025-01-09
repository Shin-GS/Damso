package com.damso.user.service.story.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.repository.story.StoryRepository;
import com.damso.user.service.story.StoryFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryFinderImpl implements StoryFinder {
    private final StoryRepository storyRepository;

    @Override
    public Story getEntity(Long storyId) {
        return storyRepository.findById(storyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }
}
