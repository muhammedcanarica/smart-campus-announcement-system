package com.smartcampus.domain.user;

import com.smartcampus.domain.notification.NotificationType;

/**
 * Stores the preferred notification channel for a user.
 */
public enum NotificationPreference {
    EMAIL(NotificationType.EMAIL),
    SMS(NotificationType.SMS),
    PUSH(NotificationType.PUSH);

    private final NotificationType notificationType;

    NotificationPreference(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }
}
