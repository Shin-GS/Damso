package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
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
    private Integer pageOrder;

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
        this.pageOrder = 0;
        this.storyType = StoryType.TEXT;
    }
}

