package com.damso.storage.entity.story.temporary;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.content.StoryPage;
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
        story.getSortedPages()
                .forEach(this::addPage);
        this.reorderPages();
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

    public TemporaryStoryPage getStoryPageByOrder(int index) {
        List<TemporaryStoryPage> orderedStoryPages = this.getEditableTemporaryStoryPages();
        if (index <= 0) {
            return orderedStoryPages.get(0);
        }

        return index < orderedStoryPages.size() ? orderedStoryPages.get(index) : orderedStoryPages.get(orderedStoryPages.size() - 1);
    }

    public void reorderPages() {
        AtomicInteger sortNumber = new AtomicInteger(0);
        this.getEditableTemporaryStoryPages()
                .forEach(storyPage -> storyPage.setPageOrder(sortNumber.getAndIncrement()));
    }

    public List<TemporaryStoryPage> getEditableTemporaryStoryPages() {
        return this.getTemporaryStoryPages().stream()
                .filter(page -> !page.isDeleted())
                .sorted(Comparator.comparing(TemporaryStoryPage::getPageOrder))
                .toList();
    }
}
