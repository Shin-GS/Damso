package com.damso.userservice.story.impl;

import com.damso.core.code.ErrorCode;
import com.damso.core.enums.story.StoryCommentStatusType;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.comment.StoryComment;
import com.damso.storage.entity.story.content.StoryPage;
import com.damso.storage.repository.story.StoryCommentRepository;
import com.damso.userservice.member.MemberFinder;
import com.damso.userservice.story.StoryCommentFinder;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.response.StoryViewCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryCommentFinderImpl implements StoryCommentFinder {
    private final StoryFinder storyFinder;
    private final StoryPageFinder storyPageFinder;
    private final MemberFinder memberFinder;
    private final StoryCommentRepository storyCommentRepository;

    @Override
    public List<StoryViewCommentResponse> findComments(Long storyId,
                                                       Long storyPageId,
                                                       Long memberId,
                                                       Pageable pageable) {
        Story story = storyFinder.getEntity(storyId);
        // todo 조회 권한 여부 체크 추가 필요
        if (!story.isPublished()) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

        StoryPage storyPage = storyPageFinder.getStoryPageEntity(storyId, storyPageId);
        Member member = memberFinder.findEntity(memberId);
        return storyCommentRepository.findByStoryAndStoryPageAndStatusOrderByIdDesc(story, storyPage, StoryCommentStatusType.NORMAL, pageable).stream()
                .sorted(Comparator.comparing(StoryComment::getId).reversed())
                .map(comment -> StoryViewCommentResponse.of(comment, member))
                .toList();
    }
}
