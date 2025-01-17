package com.damso.domain.db.repository.story;

import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.damso.domain.db.entity.story.temporary.QTemporaryStory.temporaryStory;

@Repository
@RequiredArgsConstructor
public class TemporaryStoryRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public Optional<TemporaryStory> findLatestWritingTemporaryStory(Story story) {
        return Optional.ofNullable(queryFactory.select(temporaryStory)
                .from(temporaryStory)
                .where(temporaryStory.story.eq(story),
                        temporaryStory.status.eq(StoryTemporaryStatusType.WRITING))
                .orderBy(temporaryStory.id.desc())
                .fetchFirst()
        );
    }
}
