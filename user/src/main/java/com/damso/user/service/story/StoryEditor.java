package com.damso.user.service.story;

import com.damso.user.service.story.command.StoryEditCommand;
import com.damso.user.service.story.model.StoryEditModel;

public interface StoryEditor {
    StoryEditModel create(Long memberId);

    StoryEditModel update(Long storyId,
                          Long memberId,
                          StoryEditCommand command);
}
