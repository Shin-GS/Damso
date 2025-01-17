package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryStatusType;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.story.temporary.TemporaryStory;
import com.damso.domain.db.entity.subscribe.SubscriptionPlanStory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    }

    public boolean isEditable(Member member) {
        return this.member.equals(member) && !this.status.equals(StoryStatusType.DELETED);
    }

    public void published(TemporaryStory temporaryStory) {
        this.title = temporaryStory.getTitle();
        this.commentType = temporaryStory.getCommentType();
        this.status = StoryStatusType.PUBLISHED;
    }

    public void delete() {
        this.status = StoryStatusType.DELETED;
    }
}
