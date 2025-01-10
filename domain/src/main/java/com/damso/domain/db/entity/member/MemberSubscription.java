package com.damso.domain.db.entity.member;

import com.damso.core.enums.subscribe.SubscriptionStatusType;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.subscribe.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "MEMBER_SUBSCRIPTION")
@Getter
@Setter
@NoArgsConstructor
public class MemberSubscription extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_PLAN_NO", columnDefinition = "BIGINT", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBSCRIPTION_STATUS", columnDefinition = "VARCHAR(10)", nullable = false)
    private SubscriptionStatusType status;

    @Column(name = "SUBSCRIPTION_START_DATE", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "SUBSCRIPTION_END_DATE", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;
}
