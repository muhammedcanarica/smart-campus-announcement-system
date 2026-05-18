# Smart Campus Announcement System

## Proje Hakkında

Bu proje, üniversite kampüsünde yayımlanan duyuruların ilgili kullanıcılara otomatik olarak iletilmesini simüle eden bir Java Maven console uygulamasıdır. Sistem içinde öğrenci ve öğretmen kullanıcı tipleri bulunur. Kullanıcılar kendi bildirim tercihlerini belirler ve yeni bir duyuru yayımlandığında sistem bu tercihe göre e-posta, SMS veya push bildirimi üretir.

Projede aşağıdaki temel gereksinimler uygulanmıştır:

- Console tabanlı kullanıcı girişi
- En az iki kullanıcı tipi: `StudentObserver` ve `TeacherObserver`
- Dört duyuru tipi: `ExamAnnouncement`, `EventAnnouncement`, `FoodMenuAnnouncement`, `LibraryAnnouncement`
- Üç bildirim tipi: `EmailNotification`, `SmsNotification`, `PushNotification`
- `InMemoryUserRepository` ile basit veri yönetimi
- Gerçek servis kullanılmadan konsol üzerinden bildirim gösterimi
- Observer, Factory ve Singleton tasarım desenlerinin birlikte kullanımı

## Observer Pattern Nerede Kullanıldı?

Observer Pattern, duyuru yayımlandığında sisteme kayıtlı kullanıcıların otomatik olarak bilgilendirilmesi için kullanıldı.

- `AnnouncementPublisher`, gözlemlenen nesne yani publisher rolündedir.
- `UserObserver`, tüm gözlemciler için ortak arayüzdür.
- `StudentObserver` ve `TeacherObserver`, bu arayüzü uygulayan somut gözlemci sınıflarıdır.
- `AnnouncementPublisher.publish(...)` metodu çalıştığında kayıtlı kullanıcılar tek tek dolaşılır ve her kullanıcının `update(...)` metodu çağrılır.

Bu desen sayesinde duyuru yayımlama mantığı ile kullanıcı sınıfları birbirinden ayrılmıştır. Yeni bir kullanıcı tipi eklenmek istendiğinde mevcut yayınlama kodunu değiştirmeden yalnızca yeni bir observer sınıfı eklemek yeterlidir.

## Factory Pattern Nerede Kullanıldı?

Factory Pattern, nesne oluşturma işlemlerini tek merkezde toplamak için iki farklı yerde kullanıldı.

### `AnnouncementFactory`

`AnnouncementFactory`, verilen `AnnouncementType` değerine göre uygun duyuru nesnesini üretir:

- `EXAM` -> `ExamAnnouncement`
- `EVENT` -> `EventAnnouncement`
- `FOOD_MENU` -> `FoodMenuAnnouncement`
- `LIBRARY` -> `LibraryAnnouncement`

### `NotificationFactory`

`NotificationFactory`, kullanıcının tercih ettiği `NotificationType` değerine göre uygun bildirim nesnesini üretir:

- `EMAIL` -> `EmailNotification`
- `SMS` -> `SmsNotification`
- `PUSH` -> `PushNotification`

Bu yaklaşım sayesinde nesne oluşturma sorumluluğu istemci koddan ayrılmıştır. Böylece `Main` ve `AnnouncementPublisher` sınıfları somut sınıfların nasıl oluşturulduğunu bilmek zorunda kalmaz.

## Singleton Pattern Nerede Kullanıldı?

Singleton Pattern, `Logger` sınıfında kullanıldı.

- `Logger` sınıfının constructor metodu `private` olarak tanımlandı.
- Sınıf içinde tek bir `INSTANCE` nesnesi tutuldu.
- Uygulama genelinde loglama işlemleri `Logger.getInstance().log(...)` şeklinde yapıldı.

Bu desenin amacı, uygulama boyunca tek bir ortak logger nesnesi kullanılmasını sağlamaktır. Böylece farklı sınıflar ayrı ayrı logger üretmek yerine aynı loglama servisini paylaşır.

## Katmanlı Mimari Nasıl Kuruldu?

Proje, sorumlulukları birbirinden ayırmak için katmanlı mimari ile tasarlandı.

| Katman | Paket | Görev |
| --- | --- | --- |
| Sunum katmanı | `presentation` | Uygulamanın giriş noktası olan `Main` sınıfını içerir. |
| Uygulama katmanı | `application` | İş akışını yöneten servisleri, kimlik doğrulama servisini, publisher sınıfını ve factory sınıflarını içerir. |
| Alan modeli katmanı | `domain` | Duyuru, kullanıcı, kullanıcı rolü ve bildirim kavramlarını temsil eden sınıfları içerir. |
| Altyapı katmanı | `infrastructure` | `Logger` ve `InMemoryUserRepository` gibi teknik bileşenleri içerir. |

Bu yapı sayesinde her katman kendi sorumluluğuna odaklanır:

- `presentation`, yalnızca senaryoyu başlatır.
- `application`, sistemin iş akışını yönetir.
- `domain`, iş kurallarını ve temel nesneleri tanımlar.
- `infrastructure`, veri saklama ve loglama gibi teknik ihtiyaçları karşılar.

Katmanlı mimari, kodun okunabilirliğini artırır, bakımını kolaylaştırır ve ileride yeni özellik eklemeyi daha düzenli hale getirir.

## Yapay Zeka Bu Projede Nasıl Kullanıldı?

Yapay zeka, uygulamanın çalışma zamanında kullanılan bir bileşen değildir. Projede gerçek zamanlı yapay zeka servisi, makine öğrenmesi modeli veya otomatik karar verme sistemi bulunmamaktadır.

Yapay zeka, geliştirme sürecinde yardımcı araç olarak kullanılmıştır:

- Proje yapısının planlanması
- Tasarım desenlerinin uygun sınıflara dağıtılması
- Kod iskeletinin hazırlanması
- README dokümantasyonunun düzenlenmesi
- Derleme ve çalıştırma çıktılarının kontrol edilmesi

Sonuç olarak yapay zeka, geliştiriciye yardımcı olan bir üretkenlik aracı olarak kullanılmış; uygulamanın iş mantığı ise Java sınıfları ve tasarım desenleri ile gerçekleştirilmiştir.

## Kullanıcı Girişi

Uygulama başladığında önce console tabanlı giriş ekranı gösterilir. Giriş kontrolü `AuthenticationService` tarafından yapılır ve kullanıcı bilgileri `InMemoryUserRepository` içinde tutulur.

Varsayılan yönetici bilgileri:

- Kullanıcı adı: `admin`
- Şifre: `1234`

Giriş başarılı olduğunda mevcut duyuru yayımlama senaryosu çalışır. Giriş başarısız olduğunda program `Giriş başarısız. Program sonlandırılıyor.` mesajını yazar ve sonlanır.

## Uygulama Senaryosu

`Main.java` içinde aşağıdaki senaryo çalıştırılır:

1. Kullanıcı giriş ekranında kimlik doğrulama yapılır.
2. Giriş başarılıysa duyuru senaryosu başlar.
3. Sisteme öğrenci ve öğretmen kullanıcılar eklenir.
4. Kullanıcıların bildirim tercihleri belirlenir.
5. Yönetici yeni bir sınav duyurusu oluşturur.
6. `AnnouncementFactory`, uygun duyuru nesnesini üretir.
7. Duyuru yayımlanır.
8. `AnnouncementPublisher`, Observer yapısı ile kullanıcıları otomatik olarak bilgilendirir.
9. `NotificationFactory`, her kullanıcı için uygun bildirim kanalını üretir.
10. Bildirimler konsol ekranında gösterilir.
11. `Logger`, yayımlama işlemini kaydeder.

## Çalıştırma Adımları

### Windows

1. Proje klasöründe PowerShell açın.
2. Projeyi Maven Wrapper ile derleyin:

```bash
.\mvnw.cmd clean package
```

3. Uygulamayı çalıştırın:

```bash
java -jar target\smart-campus-announcement-system-1.0-SNAPSHOT.jar
```

### Linux / macOS

```bash
./mvnw clean package
java -jar target/smart-campus-announcement-system-1.0-SNAPSHOT.jar
```

## Örnek Konsol Çıktısı

```text
=== Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi ===
Kullanıcı girişi
Kullanıcı adı: admin
Şifre: 1234
Giriş başarılı.

1. Sisteme kullanıcılar ekleniyor.
2. Bildirim tercihleri belirlendi:
   - Ayşe Yılmaz -> EMAIL
   - Mehmet Kaya -> SMS
   - Dr. Elif Demir -> PUSH
3. Yönetici yeni bir sınav duyurusu oluşturuyor.
4. AnnouncementFactory uygun duyuruyu oluşturdu: ExamAnnouncement

5. Duyuru yayımlanıyor: Ara Sınav Programı
6. Observer yapısı kullanıcıları otomatik bilgilendiriyor.
7. NotificationFactory uygun bildirim kanallarını oluşturuyor.
8. Bildirimler konsolda gösteriliyor.
   - Ayşe Yılmaz için EmailNotification oluşturuldu.
     Observer bildirimi -> Öğrenci Ayşe Yılmaz yeni duyurudan haberdar oldu.
     E-posta bildirimi -> Ayşe Yılmaz: Ara Sınav Programı - Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır.
   - Mehmet Kaya için SmsNotification oluşturuldu.
     Observer bildirimi -> Öğrenci Mehmet Kaya yeni duyurudan haberdar oldu.
     SMS bildirimi -> Mehmet Kaya: Ara Sınav Programı - Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır.
   - Dr. Elif Demir için PushNotification oluşturuldu.
     Observer bildirimi -> Öğretmen Dr. Elif Demir yeni duyurudan haberdar oldu.
     Push bildirimi -> Dr. Elif Demir: Ara Sınav Programı - Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır.
9. Logger yayımlama işlemini kaydediyor.
```
