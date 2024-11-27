package com.demo.admin.storage.entity.user;

import com.demo.admin.core.constant.NotificationType;
import com.demo.admin.storage.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserNotificationSetting extends CommonTime {
    @Id
    @Column(name = "user_notification_setting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "user_notification_agreement")
    private boolean agreement = false;

    @Column(name = "user_notification_data")
    private String data;
}
