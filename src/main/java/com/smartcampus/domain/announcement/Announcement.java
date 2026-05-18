package com.smartcampus.domain.announcement;

/**
 * Base type for all announcements.
 */
public abstract class Announcement {
    private final AnnouncementType type;
    private final String title;
    private final String message;

    protected Announcement(AnnouncementType type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public AnnouncementType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
