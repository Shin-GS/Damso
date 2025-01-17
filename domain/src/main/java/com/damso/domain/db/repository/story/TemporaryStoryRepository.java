package com.damso.domain.db.repository.story;

import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryStoryRepository extends JpaRepository<TemporaryStory, Long> {
}

