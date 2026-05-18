package com.smartcampus.domain.announcement;

/**
 * Announcement used for campus events.
 */
public class EventAnnouncement extends Announcement {
    public EventAnnouncement(String title, String message) {
        super(AnnouncementType.EVENT, title, message);
    }
}
