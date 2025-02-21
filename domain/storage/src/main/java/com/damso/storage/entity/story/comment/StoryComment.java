package com.damso.storage.entity.story.comment;

import com.damso.core.enums.story.StoryCommentStatusType;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.content.StoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "STORY_COMMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryComment extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORY_COMMENT_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @ManyToOne
    @JoinColumn(name = "STORY_PAGE_NO", columnDefinition = "BIGINT")
    private StoryPage storyPage;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_COMMENT_STATUS", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryCommentStatusType status;

    @Column(name = "STORY_COMMENT_TEXT", nullable = false, columnDefinition = "TEXT")
    private String text;

    public StoryComment(Story story,
                        StoryPage storyPage,
                        Member member,
                        String text) {
        this.story = story;
        this.storyPage = storyPage;
        this.member = member;
        this.text = text;
        this.status = StoryCommentStatusType.NORMAL;
    }

    public String getMemberName() {
        return ObjectUtils.isEmpty(member) ? "" : member.getName();
    }

    public boolean isEditable(Member member) {
        return this.member.equals(member) && this.status.equals(StoryCommentStatusType.NORMAL);
    }

    public void update(String text) {
        this.text = text;
    }

    public void delete() {
        this.status = StoryCommentStatusType.DELETED;
    }

    public Long getStoryId() {
        return ObjectUtils.isEmpty(story) ? null : story.getId();
    }

    public Long getStoryPageId() {
        return ObjectUtils.isEmpty(storyPage) ? null : storyPage.getId();
    }
}
