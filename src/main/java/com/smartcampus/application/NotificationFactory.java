package com.smartcampus.application;

import com.smartcampus.domain.notification.EmailNotification;
import com.smartcampus.domain.notification.Notification;
import com.smartcampus.domain.notification.NotificationType;
import com.smartcampus.domain.notification.PushNotification;
import com.smartcampus.domain.notification.SmsNotification;

/**
 * Factory that creates notification senders by channel.
 */
public class NotificationFactory {
    public Notification createNotification(NotificationType type) {
        return switch (type) {
            case EMAIL -> new EmailNotification();
            case SMS -> new SmsNotification();
            case PUSH -> new PushNotification();
        };
    }
}
