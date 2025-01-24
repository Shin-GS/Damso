package com.damso.storage.entity.story.temporary;

import com.damso.core.enums.story.StoryFileType;
import com.damso.storage.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TEMPORARY_STORY_FILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryStoryFile extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPORARY_STORY_FILE_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEMPORARY_STORY_PAGE_NO", columnDefinition = "BIGINT", nullable = false)
    private TemporaryStoryPage temporaryStoryPage;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_FILE_TYPE", columnDefinition = "VARCHAR(10)", nullable = false)
    private StoryFileType fileType;

    @Column(name = "STORY_FILE_PATH", columnDefinition = "VARCHAR(512)", nullable = false)
    private String filePath;

    @Column(name = "STORY_FILE_ORDER", columnDefinition = "INT DEFAULT 1")
    private Integer order;

    public TemporaryStoryFile(TemporaryStoryPage temporaryStoryPage,
                              StoryFileType fileType,
                              String filePath,
                              Integer order) {
        this.temporaryStoryPage = temporaryStoryPage;
        this.fileType = fileType;
        this.filePath = filePath;
        this.order = order;
    }

    public boolean isImage() {
        return this.fileType.equals(StoryFileType.IMAGE);
    }

    public boolean isVideo() {
        return this.fileType.equals(StoryFileType.VIDEO);
    }
}
