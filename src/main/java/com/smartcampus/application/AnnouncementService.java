package com.smartcampus.application;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.infrastructure.Logger;

/**
 * Application service that coordinates announcement publication.
 */
public class AnnouncementService {
    private final AnnouncementPublisher announcementPublisher;

    public AnnouncementService(AnnouncementPublisher announcementPublisher) {
        this.announcementPublisher = announcementPublisher;
    }

    public void publishAnnouncement(Announcement announcement) {
        System.out.println("Application Layer -> AnnouncementService yayınlama sürecini başlatıyor.");
        announcementPublisher.publish(announcement);
        System.out.println("Singleton Pattern -> Logger Singleton kayıt alıyor.");
        Logger.getInstance().log("Duyuru yayınlandı: " + announcement.getType()
                + " - " + announcement.getTitle());
    }
}
