package com.damso.domain.db.entity.story.temporary;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.story.Story;
import com.damso.domain.db.entity.story.StoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "TEMPORARY_STORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryStory extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPORARY_STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @Column(name = "STORY_TITLE", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryCommentType commentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPORARY_STORY_STATUS", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryTemporaryStatusType status;

    @OneToMany(mappedBy = "temporaryStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemporaryStoryPage> temporaryStoryPages = new ArrayList<>();

    public TemporaryStory(Story story) {
        this.story = story;
        this.title = story.getTitle();
        this.commentType = story.getCommentType();
        this.status = StoryTemporaryStatusType.WRITING;
        story.getStoryPages().stream()
                .filter(storyPage -> !storyPage.isDeleted())
                .forEach(this::addPage);
    }

    public Long getStoryId() {
        return story.getId();
    }

    private void addPage(StoryPage storyPage) {
        this.temporaryStoryPages.add(new TemporaryStoryPage(this, storyPage));
        this.reorderPages();
    }

    public void addPage() {
        this.temporaryStoryPages.add(new TemporaryStoryPage(this));
        this.reorderPages();
    }

    public void deletePage(TemporaryStoryPage temporaryStoryPage) {
        temporaryStoryPage.setDeleted(Boolean.TRUE);
        this.reorderPages();
    }

    public Optional<TemporaryStoryPage> getStoryPage(Long temporaryStoryPageId) {
        return temporaryStoryPages.stream()
                .filter(page -> page.getId().equals(temporaryStoryPageId))
                .findFirst();
    }

    public void reorderPages() {
        AtomicInteger sortNumber = new AtomicInteger(0);
        temporaryStoryPages.stream()
                .filter(storyPage -> !storyPage.isDeleted())
                .sorted(Comparator.comparingInt(TemporaryStoryPage::getPageOrder))
                .forEach(storyPage -> storyPage.setPageOrder(sortNumber.getAndIncrement()));
    }
}
