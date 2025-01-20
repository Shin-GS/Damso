package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryFileType;
import com.damso.core.enums.story.StoryType;
import com.damso.domain.db.converter.BooleanConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.story.temporary.TemporaryStoryPage;
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

    public void published(TemporaryStoryPage temporaryStoryPage) {
        this.pageOrder = temporaryStoryPage.getPageOrder();
        this.storyType = temporaryStoryPage.getStoryType();
        this.deleted = temporaryStoryPage.isDeleted();

        switch (this.storyType) {
            case TEXT -> updateStoryText(temporaryStoryPage.getStoryText(), temporaryStoryPage.getStoryPlainText());
            case IMAGE -> updateStoryImages(temporaryStoryPage.getImagePaths());
            case VIDEO -> updateStoryVideo(temporaryStoryPage.getVideoPaths());
        }
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

    private void updateStoryImages(List<String> images) {
        this.storyText = null;
        this.storyFiles.clear();
        if (ObjectUtils.isEmpty(images)) {
            return;
        }

        AtomicInteger orderCounter = new AtomicInteger(0);
        images.stream()
                .map(file -> new StoryFile(this, StoryFileType.IMAGE, file, orderCounter.getAndIncrement()))
                .forEach(storyFile -> this.storyFiles.add(storyFile));
    }

    private void updateStoryVideo(List<String> videos) {
        this.storyText = null;
        this.storyFiles.clear();
        if (ObjectUtils.isEmpty(videos)) {
            return;
        }

        this.storyFiles.add(new StoryFile(this, StoryFileType.VIDEO, videos.get(0), 0));
    }

    public String getStoryText() {
        return ObjectUtils.isEmpty(this.storyText) ? "" : this.storyText.getText();
    }

    public String getStoryPlainText() {
        return ObjectUtils.isEmpty(this.storyText) ? "" : this.storyText.getPlanText();
    }
}
