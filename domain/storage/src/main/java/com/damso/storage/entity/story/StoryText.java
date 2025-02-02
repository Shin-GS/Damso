package com.damso.storage.entity.story;

import com.damso.core.utils.common.StringUtil;
import com.damso.storage.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STORY_TEXT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryText extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORY_TEXT_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "STORY_PAGE_NO", columnDefinition = "BIGINT", nullable = false)
    private StoryPage storyPage;

    @Column(name = "STORY_TEXT", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "STORY_PLAN_TEXT", columnDefinition = "TEXT", nullable = false)
    private String plainText;

    public StoryText(StoryPage storyPage,
                     String text,
                     String plainText) {
        this.storyPage = storyPage;
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.plainText = StringUtil.defaultIfEmpty(plainText, "");
    }

    public void update(String text,
                       String plainText) {
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.plainText = StringUtil.defaultIfEmpty(plainText, "");
    }
}
