package com.smartcampus.domain.user;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.notification.Notification;

/**
 * Observer contract used by users who want campus announcements.
 */
public interface UserObserver {
    String getName();

    NotificationPreference getNotificationPreference();

    void update(Announcement announcement, Notification notification);
}
