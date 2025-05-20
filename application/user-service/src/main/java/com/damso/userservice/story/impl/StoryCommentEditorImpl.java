package com.damso.userservice.story.impl;

import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.comment.StoryComment;
import com.damso.storage.entity.story.content.StoryPage;
import com.damso.storage.repository.story.StoryCommentRepository;
import com.damso.userservice.member.MemberFinder;
import com.damso.userservice.story.StoryCommentEditor;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StoryCommentUpdateRequest;
import com.damso.userservice.story.response.StoryCommentEditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryCommentEditorImpl implements StoryCommentEditor {
    private final MemberFinder memberFinder;
    private final StoryFinder storyFinder;
    private final StoryPageFinder storyPageFinder;
    private final StoryCommentRepository storyCommentRepository;

    @Override
    public StoryCommentEditResponse createComment(Long storyId,
                                                  Long pageId,
                                                  StoryCommentUpdateRequest request,
                                                  Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyFinder.getEditableEntity(storyId);
        StoryPage storyPage = storyPageFinder.getStoryPageEntity(storyId, pageId);
        // todo 구독 권한 체크 필요

        StoryComment storyComment = storyCommentRepository.save(new StoryComment(story, storyPage, member, request.text()));
        return new StoryCommentEditResponse(storyComment);
    }

    @Override
    public StoryCommentEditResponse updateComment(Long commentId,
                                                  StoryCommentUpdateRequest request,
                                                  Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        StoryComment storyComment = storyCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        // todo 구독 권한 체크 필요

        if (!storyComment.isEditable(member)) {
            throw new BusinessException(ErrorCode.STORY_COMMENT_UNAUTHORIZED);
        }

        storyComment.update(request.text());
        return new StoryCommentEditResponse(storyComment);
    }

    @Override
    public StoryCommentEditResponse deleteComment(Long commentId,
                                                  Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        StoryComment storyComment = storyCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        // todo 구독 권한 체크 필요

        if (!storyComment.isEditable(member)) {
            throw new BusinessException(ErrorCode.STORY_COMMENT_UNAUTHORIZED);
        }

        storyComment.delete();
        return new StoryCommentEditResponse(storyComment);
    }
}
