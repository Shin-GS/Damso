package com.damso.domain.db.repository.story;

import com.damso.domain.db.entity.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
