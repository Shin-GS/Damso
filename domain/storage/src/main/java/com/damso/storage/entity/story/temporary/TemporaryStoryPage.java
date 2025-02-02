package com.damso.storage.entity.story.temporary;

import com.damso.core.enums.story.StoryFileType;
import com.damso.core.enums.story.StoryType;
import com.damso.storage.converter.BooleanConverter;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.story.StoryFile;
import com.damso.storage.entity.story.StoryPage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @OneToOne(mappedBy = "temporaryStoryPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private TemporaryStoryText temporaryStoryText;

    @OneToMany(mappedBy = "temporaryStoryPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemporaryStoryFile> temporaryStoryFiles = new ArrayList<>();

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

        if (!ObjectUtils.isEmpty(storyPage.getStoryText())) {
            this.temporaryStoryText = new TemporaryStoryText(this, storyPage.getStoryText(), storyPage.getStoryPlainText());
        }

        AtomicInteger sortNumber = new AtomicInteger(0);
        storyPage.getStoryFiles().stream()
                .sorted(Comparator.comparingInt(StoryFile::getOrder))
                .map(storyFile -> new TemporaryStoryFile(this, storyFile.getFileType(), storyFile.getFilePath(), sortNumber.getAndIncrement()))
                .forEach(temporaryStoryFile -> this.temporaryStoryFiles.add(temporaryStoryFile));
    }

    public void update(String text,
                       String plainText,
                       List<String> files) {
        switch (this.storyType) {
            case TEXT -> updateStoryText(text, plainText);
            case IMAGE -> updateStoryImages(files);
            case VIDEO -> updateStoryVideo(files);
        }
    }

    private void updateStoryText(String text,
                                 String plainText) {
        this.temporaryStoryFiles.clear();
        if (ObjectUtils.isEmpty(this.temporaryStoryText)) {
            this.temporaryStoryText = new TemporaryStoryText(this, text, plainText);
            return;
        }

        this.temporaryStoryText.update(text, plainText);
    }

    private void updateStoryImages(List<String> files) {
        this.temporaryStoryText = null;
        this.temporaryStoryFiles.clear();
        if (ObjectUtils.isEmpty(files)) {
            return;
        }

        AtomicInteger orderCounter = new AtomicInteger(0);
        files.stream()
                .map(file -> new TemporaryStoryFile(this, StoryFileType.IMAGE, file, orderCounter.getAndIncrement()))
                .forEach(storyFile -> this.temporaryStoryFiles.add(storyFile));
    }

    private void updateStoryVideo(List<String> files) {
        this.temporaryStoryText = null;
        this.temporaryStoryFiles.clear();
        if (ObjectUtils.isEmpty(files)) {
            return;
        }

        this.temporaryStoryFiles.add(new TemporaryStoryFile(this, StoryFileType.VIDEO, files.get(0), 0));
    }

    public Long getStoryPageId() {
        return ObjectUtils.isEmpty(this.storyPage) ? null : this.storyPage.getId();
    }

    public boolean isNeed() {
        return !this.deleted || !ObjectUtils.isEmpty(this.storyPage);
    }

    public String getStoryText() {
        return ObjectUtils.isEmpty(this.temporaryStoryText) ? "" : this.temporaryStoryText.getText();
    }

    public String getStoryPlainText() {
        return ObjectUtils.isEmpty(this.temporaryStoryText) ? "" : this.temporaryStoryText.getPlainText();
    }

    public List<String> getImagePaths() {
        return this.temporaryStoryFiles.stream()
                .filter(TemporaryStoryFile::isImage)
                .map(TemporaryStoryFile::getFilePath)
                .toList();
    }

    public List<String> getVideoPaths() {
        return this.temporaryStoryFiles.stream()
                .filter(TemporaryStoryFile::isVideo)
                .map(TemporaryStoryFile::getFilePath)
                .toList();
    }

    public Long getStoryId() {
        return this.storyPage.getId();
    }
}
