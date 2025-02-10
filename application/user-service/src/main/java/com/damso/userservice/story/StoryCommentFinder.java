package com.damso.userservice.story;

import com.damso.userservice.story.response.StoryViewCommentResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoryCommentFinder {
    List<StoryViewCommentResponse> findComments(Long storyId,
                                                Long storyPageId,
                                                Long memberId,
                                                Pageable pageable);
}
