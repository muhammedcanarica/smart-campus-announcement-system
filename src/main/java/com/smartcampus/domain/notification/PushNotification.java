package com.smartcampus.domain.notification;

import com.smartcampus.domain.announcement.Announcement;

/**
 * Simulates a push notification by writing to the console.
 */
public class PushNotification implements Notification {
    @Override
    public NotificationType getType() {
        return NotificationType.PUSH;
    }

    @Override
    public void send(String recipientName, Announcement announcement) {
        System.out.println("     Push bildirimi gönderildi -> " + recipientName + ": " + announcement.getTitle()
                + " - " + announcement.getMessage());
    }
}
