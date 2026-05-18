package com.smartcampus.presentation;

import com.smartcampus.application.AnnouncementFactory;
import com.smartcampus.application.AnnouncementPublisher;
import com.smartcampus.application.AnnouncementService;
import com.smartcampus.application.NotificationFactory;
import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.announcement.AnnouncementType;
import com.smartcampus.domain.user.NotificationPreference;
import com.smartcampus.domain.user.StudentObserver;
import com.smartcampus.domain.user.TeacherObserver;
import com.smartcampus.domain.user.UserObserver;
import com.smartcampus.infrastructure.InMemoryUserRepository;

/**
 * Runs the console scenario requested for the smart campus system.
 */
public class Main {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();

        // 1-2. Add users and define their notification preferences.
        System.out.println("=== Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi ===");
        System.out.println("1. Sisteme kullanıcılar ekleniyor.");
        userRepository.addUser(new StudentObserver("Ayşe Yılmaz", NotificationPreference.EMAIL));
        userRepository.addUser(new StudentObserver("Mehmet Kaya", NotificationPreference.SMS));
        userRepository.addUser(new TeacherObserver("Dr. Elif Demir", NotificationPreference.PUSH));
        System.out.println("2. Bildirim tercihleri belirlendi:");
        for (UserObserver user : userRepository.findAll()) {
            System.out.println("   - " + user.getName() + " -> " + user.getNotificationPreference());
        }

        NotificationFactory notificationFactory = new NotificationFactory();
        AnnouncementPublisher announcementPublisher = new AnnouncementPublisher(notificationFactory);
        AnnouncementService announcementService = new AnnouncementService(announcementPublisher);
        AnnouncementFactory announcementFactory = new AnnouncementFactory();

        // Register repository users as observers before publishing.
        for (UserObserver user : userRepository.findAll()) {
            announcementPublisher.registerObserver(user);
        }

        // 3-4. The administrator creates an exam announcement through the factory.
        System.out.println("3. Yönetici yeni bir sınav duyurusu oluşturuyor.");
        Announcement examAnnouncement = announcementFactory.createAnnouncement(
                AnnouncementType.EXAM,
                "Ara Sınav Programı",
                "Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır."
        );
        System.out.println("4. AnnouncementFactory uygun duyuruyu oluşturdu: "
                + examAnnouncement.getClass().getSimpleName());

        // 5-9. Publish, notify observers, create notifications, and write the log.
        announcementService.publishAnnouncement(examAnnouncement);
    }
}
