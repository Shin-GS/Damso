package com.damso.storage.entity.story.temporary;

import com.damso.core.utils.common.StringUtil;
import com.damso.storage.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TEMPORARY_STORY_TEXT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryStoryText extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPORARY_STORY_TEXT_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "TEMPORARY_STORY_PAGE_NO", columnDefinition = "BIGINT", nullable = false)
    private TemporaryStoryPage temporaryStoryPage;

    @Column(name = "STORY_TEXT", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "STORY_PLAN_TEXT", columnDefinition = "TEXT", nullable = false)
    private String planText;

    public TemporaryStoryText(TemporaryStoryPage temporaryStoryPage,
                              String text,
                              String planText) {
        this.temporaryStoryPage = temporaryStoryPage;
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.planText = StringUtil.defaultIfEmpty(planText, "");
    }

    public void update(String text,
                       String planText) {
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.planText = StringUtil.defaultIfEmpty(planText, "");
    }
}
