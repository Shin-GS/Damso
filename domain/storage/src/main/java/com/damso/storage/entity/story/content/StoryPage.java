package com.damso.storage.entity.story.content;

import com.damso.core.enums.story.StoryFileType;
import com.damso.core.enums.story.StoryType;
import com.damso.storage.converter.BooleanConverter;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.story.Story;
import com.damso.storage.entity.story.temporary.TemporaryStoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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
        return ObjectUtils.isEmpty(this.storyText) ? "" : this.storyText.getPlainText();
    }

    public List<String> getStoryFilePaths() {
        return this.getStoryFiles().stream()
                .map(StoryFile::getFilePath)
                .toList();
    }

    public Long getStoryId() {
        return this.story.getId();
    }

    public Long getPrevPageId() {
        List<StoryPage> pages = this.story.getSortedPages();
        return IntStream.range(1, pages.size())
                .filter(i -> pages.get(i).getId().equals(this.getId()))
                .mapToObj(i -> pages.get(i - 1).getId())
                .findFirst()
                .orElse(null);
    }

    public Long getNextPageId() {
        List<StoryPage> pages = this.story.getSortedPages();
        return IntStream.range(0, pages.size() - 1)
                .filter(i -> pages.get(i).getId().equals(this.getId()))
                .mapToObj(i -> pages.get(i + 1).getId())
                .findFirst()
                .orElse(null);
    }
}
