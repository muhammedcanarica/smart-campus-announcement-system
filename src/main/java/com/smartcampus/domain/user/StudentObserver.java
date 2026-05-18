package com.smartcampus.domain.user;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.notification.Notification;

/**
 * Student implementation of the observer role.
 */
public class StudentObserver implements UserObserver {
    private final String name;
    private final NotificationPreference notificationPreference;

    public StudentObserver(String name, NotificationPreference notificationPreference) {
        this.name = name;
        this.notificationPreference = notificationPreference;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NotificationPreference getNotificationPreference() {
        return notificationPreference;
    }

    @Override
    public void update(Announcement announcement, Notification notification) {
        System.out.println("     Observer bildirimi -> Öğrenci " + name
                + " yeni duyurudan haberdar oldu.");
        notification.send(name, announcement);
    }
}
