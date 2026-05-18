package com.smartcampus.domain.notification;

import com.smartcampus.domain.announcement.Announcement;

/**
 * Simulates an SMS notification by writing to the console.
 */
public class SmsNotification implements Notification {
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void send(String recipientName, Announcement announcement) {
        System.out.println("     SMS bildirimi -> " + recipientName + ": " + announcement.getTitle()
                + " - " + announcement.getMessage());
    }
}
