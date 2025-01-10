package com.damso.user.service.story.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.repository.story.StoryRepository;
import com.damso.user.service.member.MemberFinder;
import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.StoryFinder;
import com.damso.user.service.story.command.StoryEditCommand;
import com.damso.user.service.story.model.StoryEditModel;
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
    public StoryEditModel create(Long memberId) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyRepository.save(new Story(member));
        return StoryEditModel.of(story);
    }

    @Override
    public StoryEditModel update(Long storyId,
                                 Long memberId,
                                 StoryEditCommand command) {
        Member member = memberFinder.getEntity(memberId);
        Story story = storyFinder.getEntity(storyId);
        if (!story.isUpdateable(member)) {
            throw new BusinessException(ErrorCode.STORY_UNAUTHORIZED);
        }

        story.update(command.title(),
                command.storyType(),
                command.text(),
                command.planText(),
                command.files(),
                command.commentType(),
                command.published());

        Story savedStory = storyRepository.save(story);
        return StoryEditModel.of(savedStory);
    }
}
