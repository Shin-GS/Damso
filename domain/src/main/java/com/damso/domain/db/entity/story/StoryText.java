package com.damso.domain.db.entity.story;

import com.damso.domain.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STORY_TEXT")
@Getter
@Setter
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
}
