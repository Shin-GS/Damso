//package com.damso.repository.db.entity.notification;
//
//import com.damso.admin.core.constant.NotificationType;
//import com.damso.repository.db.entity.CommonTime;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//public class NotificationChannel extends CommonTime {
//    @Id
//    @Column(name = "notification_channel_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "notification_channel", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private NotificationType channel;
//}
