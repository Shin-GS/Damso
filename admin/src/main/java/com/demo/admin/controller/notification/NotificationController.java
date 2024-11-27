package com.demo.admin.controller.notification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {
    @GetMapping("/notifications")
    public String notifications() {
        return "notification/notificationList";
    }

    @GetMapping("/notifications/logs")
    public String notificationLogs() {
        return "notification/notificationLogList";
    }
}
