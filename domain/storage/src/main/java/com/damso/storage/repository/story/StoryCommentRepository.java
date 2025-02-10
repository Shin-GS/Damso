package com.damso.storage.repository.story;

import com.damso.core.enums.story.StoryCommentStatusType;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.comment.StoryComment;
import com.damso.storage.entity.story.content.StoryPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryCommentRepository extends JpaRepository<StoryComment, Long> {
    List<StoryComment> findByStoryAndStoryPageAndStatusOrderByIdDesc(Story story,
                                                                     StoryPage storyPage,
                                                                     StoryCommentStatusType status,
                                                                     Pageable pageable);
}
