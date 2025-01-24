package com.damso.storage.repository.story;

import com.damso.storage.entity.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
