package com.damso.domain.db.entity.story;

import com.damso.core.utils.common.StringUtil;
import com.damso.domain.db.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @Column(name = "STORY_TEXT", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "STORY_PLAN_TEXT", columnDefinition = "TEXT", nullable = false)
    private String planText;

    public StoryText(Story story,
                     String text,
                     String planText) {
        this.story = story;
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.planText = StringUtil.defaultIfEmpty(planText, "");
    }

    public void update(String text,
                       String planText) {
        this.text = StringUtil.defaultIfEmpty(text, "");
        this.planText = StringUtil.defaultIfEmpty(planText, "");
    }
}
