package com.damso.domain.db.entity.notification;

import com.damso.core.constant.NotificationType;
import com.damso.domain.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "NotificationChannel")
@Getter
@Setter
public class NotificationChannel extends CommonTime {
    @Id
    @Column(name = "notification_channel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_channel", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType channel;
}
