package com.smartcampus.domain.user;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.notification.Notification;

/**
 * Teacher implementation of the observer role.
 */
public class TeacherObserver implements UserObserver {
    private final String name;
    private final NotificationPreference notificationPreference;

    public TeacherObserver(String name, NotificationPreference notificationPreference) {
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
        System.out.println("     Observer bildirimi -> Öğretmen " + name
                + " yeni duyurudan haberdar oldu.");
        notification.send(name, announcement);
    }
}
