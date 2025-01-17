package com.damso.user.service.story.impl;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.repository.story.StoryRepository;
import com.damso.user.service.member.MemberFinder;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.request.StoryEditRequest;
import com.damso.user.service.story.response.StoryEditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryEditorImpl implements StoryEditor {
    private final StoryRepository storyRepository;
    private final MemberFinder memberFinder;
    private final StoryFinder storyFinder;

    @Override
    public StoryEditResponse create(Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyRepository.save(new Story(member));
        return StoryEditResponse.of(story);
    }

    @Override
    public StoryEditResponse update(Long storyId,
                                    Long memberId,
                                    StoryEditRequest request) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyFinder.getEntity(storyId);
        if (!story.isUpdateable(member)) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

//        story.update(request.title(),
//                request.storyType(),
//                StringUtil.defaultIfEmpty(request.text(), ""),
//                StringUtil.defaultIfEmpty(request.planText(), ""),
//                request.files(),
//                request.commentType());
//        story.publish(request.published());

        Story savedStory = storyRepository.save(story);
        return StoryEditResponse.of(savedStory);
    }

    @Override
    public void updateTitle(Long storyId,
                            Long memberId,
                            String title) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        story.setTitle(title);
    }

    @Override
    public void updateCommentType(Long storyId,
                                  Long memberId,
                                  StoryCommentType commentType) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        story.setCommentType(commentType);
    }

    @Override
    public void updatePublished(Long storyId,
                                Long memberId,
                                boolean published) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        story.setPublished(published);
    }
}
