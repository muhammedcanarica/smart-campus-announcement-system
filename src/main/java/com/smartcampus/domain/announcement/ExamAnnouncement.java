package com.smartcampus.domain.announcement;

/**
 * Announcement used for exam-related campus updates.
 */
public class ExamAnnouncement extends Announcement {
    public ExamAnnouncement(String title, String message) {
        super(AnnouncementType.EXAM, title, message);
    }
}
