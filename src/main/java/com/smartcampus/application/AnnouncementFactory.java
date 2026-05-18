package com.smartcampus.application;

import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.announcement.AnnouncementType;
import com.smartcampus.domain.announcement.EventAnnouncement;
import com.smartcampus.domain.announcement.ExamAnnouncement;
import com.smartcampus.domain.announcement.FoodMenuAnnouncement;
import com.smartcampus.domain.announcement.LibraryAnnouncement;

/**
 * Factory that creates the right announcement subtype for a request.
 */
public class AnnouncementFactory {
    public Announcement createAnnouncement(AnnouncementType type, String title, String message) {
        return switch (type) {
            case EXAM -> new ExamAnnouncement(title, message);
            case EVENT -> new EventAnnouncement(title, message);
            case FOOD_MENU -> new FoodMenuAnnouncement(title, message);
            case LIBRARY -> new LibraryAnnouncement(title, message);
        };
    }
}
