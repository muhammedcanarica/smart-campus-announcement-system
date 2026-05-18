package com.smartcampus.domain.announcement;

/**
 * Announcement used for cafeteria menu updates.
 */
public class FoodMenuAnnouncement extends Announcement {
    public FoodMenuAnnouncement(String title, String message) {
        super(AnnouncementType.FOOD_MENU, title, message);
    }
}
