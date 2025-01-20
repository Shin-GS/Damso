package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STORY_PAGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryPage extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORY_PAGE_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @Column(name = "PAGE_ORDER", columnDefinition = "INT", nullable = false)
    private int pageOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE_STORY_TYPE", columnDefinition = "VARCHAR(20)", nullable = false)
    private StoryType storyType;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "PAGE_DELETED", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
    private boolean deleted = false;

    @OneToOne(mappedBy = "storyPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private StoryText storyText;

    @OneToMany(mappedBy = "storyPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryFile> storyFiles = new ArrayList<>();

    public StoryPage(Story story) {
        this.story = story;
        this.pageOrder = 99;
        this.storyType = StoryType.TEXT;
    }

    public StoryPage(Story story,
                     TemporaryStoryPage temporaryStoryPage) {
        this.story = story;
        this.pageOrder = temporaryStoryPage.getPageOrder();
        this.storyType = temporaryStoryPage.getStoryType();
    }

    public void published(TemporaryStoryPage temporaryStoryPage) {
        this.pageOrder = temporaryStoryPage.getPageOrder();
        this.storyType = temporaryStoryPage.getStoryType();
        this.deleted = temporaryStoryPage.isDeleted();
    }
}

