package com.damso.storage.repository.story;

import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.storage.entity.story.Story;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.damso.storage.entity.story.temporary.QTemporaryStory.temporaryStory;
import static com.damso.storage.entity.story.temporary.QTemporaryStoryPage.temporaryStoryPage;

@Repository
@RequiredArgsConstructor
public class StoryPageRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public long countByNotDeleted(Story story) {
        return Optional.ofNullable(queryFactory
                        .select(temporaryStoryPage.count())
                        .from(temporaryStory)
                        .join(temporaryStoryPage).on(temporaryStoryPage.temporaryStory.eq(temporaryStory))
                        .where(
                                temporaryStory.story.eq(story),
                                temporaryStory.status.eq(StoryTemporaryStatusType.WRITING),
                                temporaryStoryPage.deleted.isFalse())
                        .fetchOne())
                .orElse(0L);
    }
}
