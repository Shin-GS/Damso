package com.damso.user.service.story.impl;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.damso.domain.db.repository.story.StoryRepository;
import com.damso.domain.db.repository.story.TemporaryStoryRepository;
import com.damso.domain.db.repository.story.TemporaryStoryRepositorySupport;
import com.damso.user.service.member.MemberFinder;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.response.StoryEditInfoResponse;
import com.damso.user.service.story.response.StoryEditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryEditorImpl implements StoryEditor {
    private final StoryRepository storyRepository;
    private final TemporaryStoryRepository temporaryStoryRepository;
    private final TemporaryStoryRepositorySupport temporaryStoryRepositorySupport;
    private final MemberFinder memberFinder;
    private final StoryFinder storyFinder;

    @Override
    public StoryEditResponse create(Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyRepository.save(new Story(member));
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        return StoryEditResponse.of(temporaryStory);
    }

    @Override
    public StoryEditInfoResponse resolveTemporaryEditInfo(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        return StoryEditInfoResponse.of(temporaryStory);
    }

    @Override
    public void updateTitle(Long storyId,
                            Long memberId,
                            String title) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        temporaryStory.setTitle(title);
    }

    @Override
    public void updateCommentType(Long storyId,
                                  Long memberId,
                                  StoryCommentType commentType) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        temporaryStory.setCommentType(commentType);
    }

    @Override
    public void reset(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        temporaryStory.setStatus(StoryTemporaryStatusType.RESET);
    }

    @Override
    public void published(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        temporaryStory.setStatus(StoryTemporaryStatusType.PUBLISHED);
        story.published(temporaryStory);
    }

    @Override
    public void delete(Long storyId, Long memberId) {
        Story story = storyFinder.getEditableEntity(storyId, memberId);
        TemporaryStory temporaryStory = resolveTemporaryStory(story);

        temporaryStory.setStatus(StoryTemporaryStatusType.DELETED);
        story.delete();
    }

    private TemporaryStory resolveTemporaryStory(Story story) {
        return temporaryStoryRepositorySupport.findLatestWritingTemporaryStory(story)
                .orElseGet(() -> temporaryStoryRepository.save(new TemporaryStory(story)));
    }
}
