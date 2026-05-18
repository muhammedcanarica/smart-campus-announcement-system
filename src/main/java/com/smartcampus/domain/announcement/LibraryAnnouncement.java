package com.smartcampus.domain.announcement;

/**
 * Announcement used for library updates.
 */
public class LibraryAnnouncement extends Announcement {
    public LibraryAnnouncement(String title, String message) {
        super(AnnouncementType.LIBRARY, title, message);
    }
}
