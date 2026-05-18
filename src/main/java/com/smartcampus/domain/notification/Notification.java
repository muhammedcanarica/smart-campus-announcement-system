package com.smartcampus.domain.notification;

import com.smartcampus.domain.announcement.Announcement;

/**
 * Contract implemented by every notification channel.
 */
public interface Notification {
    NotificationType getType();

    void send(String recipientName, Announcement announcement);
}
