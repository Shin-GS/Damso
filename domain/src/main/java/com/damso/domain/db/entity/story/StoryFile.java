package com.damso.domain.db.entity.story;

import com.damso.core.enums.story.StoryFileType;
import com.damso.domain.db.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STORY_FILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryFile extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORY_FILE_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @Enumerated(EnumType.STRING)
    @Column(name = "STORY_FILE_TYPE", columnDefinition = "VARCHAR(10)", nullable = false)
    private StoryFileType fileType;

    @Column(name = "STORY_FILE_PATH", columnDefinition = "VARCHAR(512)", nullable = false)
    private String filePath;

    @Column(name = "STORY_FILE_ORDER", columnDefinition = "INT DEFAULT 1")
    private Integer order;

    public StoryFile(Story story,
                     StoryFileType fileType,
                     String filePath,
                     Integer order) {
        this.story = story;
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
