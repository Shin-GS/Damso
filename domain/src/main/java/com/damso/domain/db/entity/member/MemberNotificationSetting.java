package com.damso.domain.db.entity.member;

import com.damso.core.constant.NotificationType;
import com.damso.domain.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MemberNotificationSetting")
@Getter
@Setter
public class MemberNotificationSetting extends CommonTime {
    @Id
    @Column(name = "member_notification_setting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "member_notification_agreement")
    private boolean agreement = false;

    @Column(name = "member_notification_data")
    private String data;
}
