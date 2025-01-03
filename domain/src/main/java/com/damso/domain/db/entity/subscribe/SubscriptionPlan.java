package com.damso.domain.db.entity.subscribe;

import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBSCRIPTION_PLAN")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionPlan extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIPTION_PLAN_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @Column(name = "SUBSCRIPTION_PLAN_NAME", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "SUBSCRIPTION_PLAN_DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "SUBSCRIPTION_PLAN_CURRENCY", columnDefinition = "VARCHAR(3) DEFAULT 'USD'", nullable = false, length = 3)
    private String currency; //USD, KRW 등 화폐 단위

    @Column(name = "SUBSCRIPTION_PLAN_PRICE", columnDefinition = "DECIMAL(10, 2)", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "SUBSCRIPTION_PLAN_ORDER", columnDefinition = "INT DEFAULT 1", nullable = false)
    private int order;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlanStory> subscriptionPlanStories = new ArrayList<>();
}
