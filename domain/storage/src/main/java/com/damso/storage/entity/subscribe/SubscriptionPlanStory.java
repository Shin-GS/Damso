package com.damso.storage.entity.subscribe;

import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.story.Story;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SUBSCRIPTION_PLAN_STORY")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionPlanStory extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIPTION_PLAN_STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORY_NO", columnDefinition = "BIGINT", nullable = false)
    private Story story;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_PLAN_NO", columnDefinition = "BIGINT", nullable = false)
    private SubscriptionPlan subscriptionPlan;
}
