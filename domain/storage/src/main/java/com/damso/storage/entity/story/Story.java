package com.damso.storage.entity.story;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryStatusType;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.content.StoryPage;
import com.damso.storage.entity.story.temporary.TemporaryStory;
import com.damso.storage.entity.story.temporary.TemporaryStoryPage;
import com.damso.storage.entity.subscribe.SubscriptionPlanStory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "STORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Story extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_STATUS", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryStatusType status;

    @Column(name = "STORY_TITLE", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryCommentType commentType;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryPage> storyPages = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlanStory> subscriptionPlanStories = new ArrayList<>();

    public Story(Member member) {
        this.member = member;
        this.title = "NEW STORY";
        this.status = StoryStatusType.CREATED;
        this.commentType = StoryCommentType.ALL;
        this.storyPages.add(new StoryPage(this));
        this.reorderPages();
    }

    public boolean isEditable() {
        return !this.status.equals(StoryStatusType.DELETED);
    }

    public boolean isEditable(Member member) {
        return this.member.equals(member) && !this.status.equals(StoryStatusType.DELETED);
    }

    public boolean isPublished() {
        return this.status.equals(StoryStatusType.PUBLISHED);
    }

    public void published(TemporaryStory temporaryStory) {
        this.title = temporaryStory.getTitle();
        this.commentType = temporaryStory.getCommentType();
        this.status = StoryStatusType.PUBLISHED;

        temporaryStory.getTemporaryStoryPages().stream()
                .filter(TemporaryStoryPage::isNeed)
                .forEach(this::createOrPublishStoryPage);
        this.reorderPages();
    }

    private void createOrPublishStoryPage(TemporaryStoryPage temporaryStoryPage) {
        if (ObjectUtils.isEmpty(temporaryStoryPage.getStoryPageId())) {
            StoryPage storyPage = new StoryPage(this);
            storyPage.published(temporaryStoryPage);
            this.storyPages.add(storyPage);
            return;
        }

        this.getStoryPage(temporaryStoryPage.getStoryPageId())
                .ifPresent(storyPage -> storyPage.published(temporaryStoryPage));
    }

    public void delete() {
        this.status = StoryStatusType.DELETED;
    }

    public Optional<StoryPage> getStoryPage(Long storyPageId) {
        return storyPages.stream()
                .filter(page -> page.getId().equals(storyPageId))
                .findFirst();
    }

    public void reorderPages() {
        AtomicInteger sortNumber = new AtomicInteger(0);
        this.getSortedPages()
                .forEach(storyPage -> storyPage.setPageOrder(sortNumber.getAndIncrement()));
    }

    public List<StoryPage> getSortedPages() {
        return this.storyPages.stream()
                .filter(page -> !page.isDeleted())
                .sorted(Comparator.comparing(StoryPage::getPageOrder))
                .toList();
    }

    public String getCreatorName() {
        return ObjectUtils.isEmpty(this.member) ? "" : this.member.getName();
    }
}
