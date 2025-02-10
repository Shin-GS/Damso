package com.damso.userservice.story.impl;

import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.comment.StoryComment;
import com.damso.storage.entity.story.content.StoryPage;
import com.damso.storage.repository.story.StoryCommentRepository;
import com.damso.userservice.member.MemberFinder;
import com.damso.userservice.story.StoryCommentEditor;
import com.damso.userservice.story.StoryFinder;
import com.damso.userservice.story.StoryPageFinder;
import com.damso.userservice.story.request.StoryCommentCreateRequest;
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
    public void createComment(Long storyId,
                              Long pageId,
                              Long memberId,
                              StoryCommentCreateRequest request) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyFinder.getEditableEntity(storyId);
        StoryPage storyPage = storyPageFinder.getStoryPageEntity(storyId, pageId);
        // todo 권한 체크 필요

        storyCommentRepository.save(new StoryComment(story, storyPage, member, request.text()));
    }
}
