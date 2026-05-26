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
            System.out.println("   Observer Pattern -> " + observer.getClass().getSimpleName()
                    + " abone edildi: " + observer.getName());
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
        System.out.println("--------------------------------------------------");
        System.out.println("AnnouncementPublisher -> Duyuru yayınlanıyor: " + announcement.getTitle());
        System.out.println("Duyuru tipi: " + announcement.getType());
        System.out.println("Mesaj: " + announcement.getMessage());
        System.out.println("Observer Pattern -> Kayıtlı kullanıcılar otomatik bilgilendiriliyor.");

        for (UserObserver observer : observers) {
            System.out.println("   Observer Pattern -> " + observer.getClass().getSimpleName()
                    + ".update(...) çağrılıyor: " + observer.getName());
            Notification notification = notificationFactory.createNotification(
                    observer.getNotificationPreference().getNotificationType()
            );
            System.out.println("   NotificationFactory -> " + observer.getNotificationPreference()
                    + " tercihi için " + notification.getClass().getSimpleName() + " oluşturdu.");
            observer.update(announcement, notification);
        }
    }
}
