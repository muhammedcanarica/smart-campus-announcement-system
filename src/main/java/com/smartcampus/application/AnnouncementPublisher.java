package com.smartcampus.application;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.notification.Notification;
import com.smartcampus.domain.user.UserObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject role of the Observer pattern.
 */
public class AnnouncementPublisher {
    private final List<UserObserver> observers = new ArrayList<>();
    private final NotificationFactory notificationFactory;

    public AnnouncementPublisher(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    public void registerObserver(UserObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }

    /**
     * Publishes an announcement and automatically notifies every observer.
     */
    public void publish(Announcement announcement) {
        System.out.println();
        System.out.println("5. Duyuru yayımlanıyor: " + announcement.getTitle());
        System.out.println("6. Observer yapısı kullanıcıları otomatik bilgilendiriyor.");
        System.out.println("7. NotificationFactory uygun bildirim kanallarını oluşturuyor.");
        System.out.println("8. Bildirimler konsolda gösteriliyor.");

        for (UserObserver observer : observers) {
            Notification notification = notificationFactory.createNotification(
                    observer.getNotificationPreference().getNotificationType()
            );
            System.out.println("   - " + observer.getName() + " için "
                    + notification.getClass().getSimpleName() + " oluşturuldu.");
            observer.update(announcement, notification);
        }
    }
}
