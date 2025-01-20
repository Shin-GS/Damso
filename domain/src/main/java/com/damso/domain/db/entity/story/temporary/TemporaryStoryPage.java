package com.damso.domain.db.entity.story.temporary;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.story.StoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "TEMPORARY_STORY_PAGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryStoryPage extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPORARY_STORY_PAGE_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEMPORARY_STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private TemporaryStory temporaryStory;

    @Column(name = "PAGE_ORDER", columnDefinition = "INT", nullable = false)
    private int pageOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE_STORY_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryType storyType;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "PAGE_DELETED", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "STORY_PAGE_NO", columnDefinition = "BIGINT")
    private StoryPage storyPage;

    public TemporaryStoryPage(TemporaryStory temporaryStory) {
        this.temporaryStory = temporaryStory;
        this.pageOrder = 99;
        this.storyType = StoryType.TEXT;
    }

    public TemporaryStoryPage(TemporaryStory temporaryStory,
                              StoryPage storyPage) {
        this.temporaryStory = temporaryStory;
        this.pageOrder = storyPage.getPageOrder();
        this.storyType = storyPage.getStoryType();
        this.storyPage = storyPage;
    }

    public Long getStoryPageId() {
        return ObjectUtils.isEmpty(this.storyPage) ? null : this.storyPage.getId();
    }

    public boolean isNeed() {
        return !this.deleted || !ObjectUtils.isEmpty(this.storyPage);
    }
}
