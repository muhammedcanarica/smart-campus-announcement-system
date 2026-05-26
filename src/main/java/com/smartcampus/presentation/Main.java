package com.smartcampus.presentation;

import com.smartcampus.application.AnnouncementFactory;
import com.smartcampus.application.AnnouncementPublisher;
import com.smartcampus.application.AnnouncementService;
import com.smartcampus.application.AuthenticationService;
import com.smartcampus.application.NotificationFactory;
import com.smartcampus.domain.announcement.Announcement;
import com.smartcampus.domain.announcement.AnnouncementType;
import com.smartcampus.domain.user.NotificationPreference;
import com.smartcampus.domain.user.StudentObserver;
import com.smartcampus.domain.user.TeacherObserver;
import com.smartcampus.domain.user.UserObserver;
import com.smartcampus.infrastructure.InMemoryUserRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Runs the console scenario requested for the smart campus system.
 */
public class Main {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        AuthenticationService authenticationService = new AuthenticationService(userRepository);
        Scanner scanner = new Scanner(System.in);
        boolean demoMode = args.length > 0 && "--demo".equalsIgnoreCase(args[0]);

        System.out.println("=== Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi ===");

        if (demoMode) {
            System.out.println("Demo modu aktif. Giriş adımı otomatik geçildi.");
        } else {
            System.out.println("Kullanıcı girişi");
            System.out.print("Kullanıcı adı: ");
            String username = scanner.nextLine();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();

            if (!authenticationService.authenticate(username, password)) {
                System.out.println("Giriş başarısız. Program sonlandırılıyor.");
                return;
            }

            System.out.println("Giriş başarılı.");
        }
        System.out.println();

        System.out.println("1. Sisteme kullanıcılar ekleniyor ve bildirim tercihleri belirleniyor.");
        userRepository.addUser(new StudentObserver("Ayşe Yılmaz", NotificationPreference.EMAIL));
        userRepository.addUser(new StudentObserver("Mehmet Kaya", NotificationPreference.SMS));
        userRepository.addUser(new TeacherObserver("Dr. Elif Demir", NotificationPreference.PUSH));

        System.out.println("   Kayıtlı kullanıcılar:");
        for (UserObserver user : userRepository.findAll()) {
            System.out.println("   - " + user.getName()
                    + " | Rol: " + getRoleName(user)
                    + " | Bildirim tercihi: " + user.getNotificationPreference());
        }

        NotificationFactory notificationFactory = new NotificationFactory();
        AnnouncementPublisher announcementPublisher = new AnnouncementPublisher(notificationFactory);
        AnnouncementService announcementService = new AnnouncementService(announcementPublisher);
        AnnouncementFactory announcementFactory = new AnnouncementFactory();

        System.out.println();
        System.out.println("2. Kullanıcılar AnnouncementPublisher'a observer olarak ekleniyor.");
        for (UserObserver user : userRepository.findAll()) {
            announcementPublisher.registerObserver(user);
        }

        System.out.println();
        System.out.println("3. Yönetici iki farklı duyuru tipi oluşturuyor.");
        List<Announcement> announcements = List.of(
                createAnnouncement(
                        announcementFactory,
                        AnnouncementType.EXAM,
                        "Ara Sınav Programı",
                        "Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır."
                ),
                createAnnouncement(
                        announcementFactory,
                        AnnouncementType.EVENT,
                        "Kariyer Günleri Etkinliği",
                        "Teknoloji firmalarının katılacağı kariyer etkinliği çarşamba günü konferans salonunda yapılacaktır."
                )
        );

        System.out.println();
        System.out.println("4. Duyurular sırayla yayınlanıyor; Observer, Factory ve Singleton akışı konsolda izlenebilir.");
        for (Announcement announcement : announcements) {
            announcementService.publishAnnouncement(announcement);
        }
    }

    private static Announcement createAnnouncement(
            AnnouncementFactory announcementFactory,
            AnnouncementType type,
            String title,
            String message
    ) {
        Announcement announcement = announcementFactory.createAnnouncement(type, title, message);
        System.out.println("   Factory Pattern -> AnnouncementFactory, " + getAnnouncementTypeName(type)
                + " için " + announcement.getClass().getSimpleName() + " oluşturdu.");
        return announcement;
    }

    private static String getRoleName(UserObserver user) {
        if (user instanceof StudentObserver) {
            return "Öğrenci";
        }
        if (user instanceof TeacherObserver) {
            return "Öğretmen";
        }
        return "Kullanıcı";
    }

    private static String getAnnouncementTypeName(AnnouncementType type) {
        return switch (type) {
            case EXAM -> "Sınav";
            case EVENT -> "Etkinlik";
            case FOOD_MENU -> "Yemekhane";
            case LIBRARY -> "Kütüphane";
        };
    }
}
