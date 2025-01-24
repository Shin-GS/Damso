package com.damso.storage.repository.story;

import com.damso.storage.entity.story.temporary.TemporaryStory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryStoryRepository extends JpaRepository<TemporaryStory, Long> {
}

