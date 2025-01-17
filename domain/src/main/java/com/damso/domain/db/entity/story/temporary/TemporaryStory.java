package com.damso.domain.db.entity.story.temporary;

import com.damso.core.enums.story.StoryCommentType;
import com.damso.core.enums.story.StoryTemporaryStatusType;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.story.Story;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public TemporaryStory(Story story) {
        this.story = story;
        this.title = story.getTitle();
        this.commentType = story.getCommentType();
        this.status = StoryTemporaryStatusType.WRITING;
    }

    public Long getStoryId() {
        return story.getId();
    }
}
