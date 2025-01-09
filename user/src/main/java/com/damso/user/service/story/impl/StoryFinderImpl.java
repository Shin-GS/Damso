package com.damso.user.service.story.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.repository.story.StoryRepository;
import com.damso.user.service.member.MemberFinder;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.model.StoryEditInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryFinderImpl implements StoryFinder {
    private final StoryRepository storyRepository;
    private final MemberFinder memberFinder;

    @Override
    public Story getEntity(Long storyId) {
        return storyRepository.findById(storyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public StoryEditInfoModel getEditInfo(Long storyId, Long memberId) {
        Story story = getEntity(storyId);
        Member member = memberFinder.getEntity(memberId);
        if (!story.isUpdateable(member)) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

        return StoryEditInfoModel.of(story);
    }
}
