package com.damso.user.service.story.impl;

import com.damso.user.service.story.StoryEditor;
import com.damso.user.service.story.model.CreateStoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryEditorImpl implements StoryEditor {
    @Override
    public CreateStoryModel create(Long memberId) {
        return CreateStoryModel.of(1L);
    }
}
