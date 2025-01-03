package com.damso.user.service.story;

import com.damso.user.service.story.model.CreateStoryModel;

public interface StoryEditor {
    CreateStoryModel create(Long memberId);
}
