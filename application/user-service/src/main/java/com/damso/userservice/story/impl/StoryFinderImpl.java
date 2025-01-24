package com.damso.userservice.story.impl;

import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;
import com.damso.storage.repository.story.StoryRepository;
import com.damso.userservice.member.MemberFinder;
import com.damso.userservice.story.StoryFinder;
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
    public Story getEditableEntity(Long storyId, Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        Story story = getEntity(storyId);
        if (story.isEditable(member)) {
            return story;
        }

        throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
    }
}
