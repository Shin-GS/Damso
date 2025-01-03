package com.damso.domain.db.entity.story;

import com.damso.core.constant.story.StoryCommentType;
import com.damso.core.constant.story.StoryType;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.subscribe.SubscriptionPlanStory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STORY")
@Getter
@Setter
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
    @Column(name = "STORY_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryType storyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_TYPE", columnDefinition = "VARCHAR(20)")
    private StoryCommentType commentType;

    @OneToOne(mappedBy = "story", cascade = CascadeType.ALL)
    private StoryText storyText;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryFile> storyFiles = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlanStory> subscriptionPlanStories = new ArrayList<>();

    // todo 카테고리

    // todo 해시태그
}
