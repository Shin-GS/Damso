package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryFileType;
import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.subscribe.SubscriptionPlanStory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "STORY_TITLE", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "STORY_DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryType storyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryCommentType commentType;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "STORY_PUBLISHED", columnDefinition = "CHAR(1) DEFAULT 'Y'", nullable = false)
    private boolean published = true;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "STORY_DELETED", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
    private boolean deleted = false;

    @OneToOne(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private StoryText storyText;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryFile> storyFiles = new ArrayList<>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlanStory> subscriptionPlanStories = new ArrayList<>();

    public Story(Member member) {
        this.member = member;
        this.title = "NEW STORY";
        this.storyType = StoryType.TEXT;
        this.commentType = StoryCommentType.ALL;
        this.published = Boolean.FALSE;
    }

    public void update(String title,
                       StoryType storyType,
                       String text,
                       String planText,
                       List<String> files,
                       StoryCommentType commentType) {
        this.title = title;
        this.storyType = storyType;
        this.commentType = commentType;

        switch (storyType) {
            case TEXT -> updateStoryText(text, planText);
            case IMAGE -> updateStoryImages(files);
            case VIDEO -> updateStoryVideo(files);
        }
    }

    public void publish(Boolean published) {
        this.published = ObjectUtils.isEmpty(published) ? Boolean.FALSE : published;
    }

    private void updateStoryText(String text,
                                 String plainText) {
        this.storyFiles.clear();
        if (ObjectUtils.isEmpty(this.storyText)) {
            this.storyText = new StoryText(this, text, plainText);
            return;
        }

        this.storyText.update(text, plainText);
    }

    private void updateStoryImages(List<String> files) {
        this.storyText = null;
        this.storyFiles.clear();
        if (ObjectUtils.isEmpty(files)) {
            return;
        }

        AtomicInteger orderCounter = new AtomicInteger(0);
        files.stream()
                .map(file -> new StoryFile(this, StoryFileType.IMAGE, file, orderCounter.getAndIncrement()))
                .forEach(storyFile -> this.storyFiles.add(storyFile));
    }

    private void updateStoryVideo(List<String> files) {
        this.storyText = null;
        this.storyFiles.clear();
        if (ObjectUtils.isEmpty(files)) {
            return;
        }

        this.storyFiles.add(new StoryFile(this, StoryFileType.VIDEO, files.get(0), 0));
    }

    public boolean isUpdateable(Member member) {
        return this.member.equals(member) && !this.deleted;
    }

    // todo 카테고리

    // todo 해시태그
}
