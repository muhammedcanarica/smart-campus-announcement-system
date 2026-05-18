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
        announcementPublisher.publish(announcement);
        System.out.println("9. Logger yayımlama işlemini kaydediyor.");
        Logger.getInstance().log("Duyuru yayımlandı: " + announcement.getType()
                + " - " + announcement.getTitle());
    }
}
