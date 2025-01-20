package com.damso.domain.db.repository.story;

import com.damso.domain.db.entity.story.Story;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.damso.domain.db.entity.story.QStoryPage.storyPage;

@Repository
@RequiredArgsConstructor
public class StoryPageRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public long countByNotDeleted(Story story) {
        return Optional.ofNullable(queryFactory
                        .select(storyPage.count())
                        .from(storyPage)
                        .where(storyPage.story.eq(story),
                                storyPage.deleted.isFalse())
                        .fetchOne())
                .orElse(0L);
    }
}
