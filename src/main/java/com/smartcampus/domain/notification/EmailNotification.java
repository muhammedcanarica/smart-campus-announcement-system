package com.smartcampus.domain.notification;

import com.smartcampus.domain.announcement.Announcement;

/**
 * Simulates an email notification by writing to the console.
 */
public class EmailNotification implements Notification {
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(String recipientName, Announcement announcement) {
        System.out.println("     E-posta bildirimi -> " + recipientName + ": " + announcement.getTitle()
                + " - " + announcement.getMessage());
    }
}
