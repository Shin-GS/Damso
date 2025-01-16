package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.member.Member;
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

    @Column(name = "STORY_TITLE", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "STORY_DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryCommentType commentType;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "STORY_PUBLISHED", columnDefinition = "CHAR(1) DEFAULT 'Y'", nullable = false)
    private boolean published = true;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "STORY_DELETED", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryPage> storyPages = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlanStory> subscriptionPlanStories = new ArrayList<>();

    public Story(Member member) {
        this.member = member;
        this.title = "NEW STORY";
        this.commentType = StoryCommentType.ALL;
        this.published = Boolean.FALSE;
        // todo StoryPage
    }

    public boolean isUpdateable(Member member) {
        return this.member.equals(member) && !this.deleted;
    }
}
