# Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi

## Proje Adı

Akıllı Kampüs Duyuru ve Bildirim Yönetim Sistemi

## Proje Amacı

Bu proje, BİL 3204 Yazılım Mimari ve Tasarımı final ödevi için hazırlanmış Java Maven console uygulamasıdır. Amaç; kampüs duyurularının farklı kullanıcı tiplerine, kullanıcıların tercih ettiği bildirim kanalları üzerinden iletilmesini simüle ederken Observer Pattern, Factory Pattern, Singleton Pattern ve katmanlı mimariyi çalışan bir örnekle göstermektir.

Web GUI demo, Java console uygulamasındaki duyuru ve bildirim akışını görsel olarak simüle eder. Ana çalışan uygulama Java Maven console uygulamasıdır.

## GitHub Pages Web Demo

Statik demo dosyaları `docs/` klasöründedir:

- `docs/index.html`
- `docs/style.css`
- `docs/app.js`

GitHub Pages ayarı yapılırken kaynak klasör olarak `docs/` seçilebilir. Web GUI demo, Java console uygulamasındaki duyuru ve bildirim akışını görsel olarak simüle eder. Ana çalışan uygulama Java Maven console uygulamasıdır.

Demo linki: [https://muhammedcanarica.github.io/smart-campus-announcement-system/](https://muhammedcanarica.github.io/smart-campus-announcement-system/)

## Kullanılan Teknolojiler

- Java 17
- Maven
- Console tabanlı Java uygulaması
- HTML, CSS ve JavaScript ile statik GitHub Pages demosu
- In-memory veri yönetimi

## Mimari Katmanlar

### Presentation Layer

`com.smartcampus.presentation` paketi uygulamanın giriş noktasını içerir. `Main` sınıfı kullanıcı girişini alır, örnek senaryoyu başlatır ve uygulama akışını console üzerinden gösterir.

### Application Layer

`com.smartcampus.application` paketi iş akışını koordine eder. `AuthenticationService`, `AnnouncementService`, `AnnouncementPublisher`, `AnnouncementFactory` ve `NotificationFactory` bu katmanda yer alır.

### Domain Layer

`com.smartcampus.domain` paketi sistemin temel kavramlarını içerir. Duyuru sınıfları, bildirim arayüzleri, kullanıcı observer sınıfları, enum değerleri ve abstract/interface yapıları bu katmanda bulunur.

### Infrastructure Layer

`com.smartcampus.infrastructure` paketi teknik destek sınıflarını içerir. `InMemoryUserRepository` basit veri yönetimini, `Logger` ise Singleton loglama mekanizmasını sağlar.

## Kullanılan Tasarım Desenleri

### Observer Pattern

Observer Pattern, duyuru yayınlandığında sisteme kayıtlı kullanıcıların otomatik olarak bilgilendirilmesi için kullanılır.

- `AnnouncementPublisher`: Subject/publisher rolündedir.
- `UserObserver`: Observer arayüzüdür.
- `StudentObserver`: Öğrenci kullanıcılar için somut observer sınıfıdır.
- `TeacherObserver`: Öğretmen kullanıcılar için somut observer sınıfıdır.

`AnnouncementPublisher.publish(...)` çalıştığında kayıtlı observer nesneleri dolaşılır ve her kullanıcı için `update(...)` metodu çağrılır.

### Factory Pattern

Factory Pattern, nesne oluşturma sorumluluğunu merkezi sınıflara taşımak için kullanılır.

- `AnnouncementFactory`: `AnnouncementType` değerine göre `ExamAnnouncement`, `EventAnnouncement`, `FoodMenuAnnouncement` veya `LibraryAnnouncement` nesnesi oluşturur.
- `NotificationFactory`: `NotificationType` değerine göre `EmailNotification`, `SmsNotification` veya `PushNotification` nesnesi oluşturur.

Bu sayede `Main` ve `AnnouncementPublisher` somut sınıfların oluşturulma detaylarına bağımlı kalmaz.

### Singleton Pattern

Singleton Pattern, `Logger` sınıfında kullanılır. `Logger` constructor metodu private olduğu için dışarıdan yeni logger nesnesi oluşturulamaz. Uygulama genelinde tek logger örneğine `Logger.getInstance()` ile erişilir.

Console çıktısında her duyuru yayınlandıktan sonra `Logger Singleton kayıt alıyor` mesajı ve log satırı görülebilir.

## Uygulama Senaryosu

1. Kullanıcı console üzerinden giriş yapar.
2. Varsayılan yönetici hesabı doğrulanır.
3. Sisteme üç kullanıcı eklenir:
   - Ayşe Yılmaz - Öğrenci - Email
   - Mehmet Kaya - Öğrenci - SMS
   - Dr. Elif Demir - Öğretmen - Push
4. Kullanıcılar `AnnouncementPublisher` nesnesine observer olarak kaydedilir.
5. `AnnouncementFactory` iki farklı duyuru üretir:
   - `ExamAnnouncement`
   - `EventAnnouncement`
6. `AnnouncementService` duyuruları yayınlama sürecini başlatır.
7. `AnnouncementPublisher` observer kullanıcıları bilgilendirir.
8. `NotificationFactory` kullanıcı tercihine uygun bildirim kanalını oluşturur.
9. Email, SMS ve Push bildirimleri console çıktısında simüle edilir.
10. `Logger` Singleton yayınlama işlemini kaydeder.

Varsayılan giriş bilgileri:

- Kullanıcı adı: `admin`
- Şifre: `1234`

## Nasıl Çalıştırılır?

### Kolay çalıştırma

Windows üzerinde proje kökündeki `Baslat.bat` dosyasına çift tıklanabilir. Dosya açıldığında şu menü gelir:

```text
1 - Java Console Uygulamasını Çalıştır
2 - Web GUI Demo Aç
3 - Çıkış
```

`Baslat.bat`, çalıştırıldığı konum ne olursa olsun kendi bulunduğu proje köküne geçer. Java uygulamasını çalıştırırken `-Dfile.encoding=UTF-8` parametresini kullanır ve JAR dosyasını `target/smart-campus-announcement-system-1.0-SNAPSHOT.jar` yolundan başlatır.

Teslim sırasında hocaya kısa yönlendirme için `TESLIM_NOTU.txt` dosyası da eklenmiştir.

### Maven ile çalıştırma

Proje kök dizininde:

```bash
mvn clean package
```

Windows üzerinde Maven Wrapper ile:

```bash
.\mvnw.cmd clean package
```

Linux/macOS üzerinde Maven Wrapper ile:

```bash
./mvnw clean package
```

### jar ile çalıştırma

Derleme tamamlandıktan sonra:

```bash
java -jar target/smart-campus-announcement-system-1.0-SNAPSHOT.jar
```

Giriş ekranını otomatik geçip örnek senaryoyu doğrudan çalıştırmak için:

```bash
java -jar target/smart-campus-announcement-system-1.0-SNAPSHOT.jar --demo
```

Windows PowerShell üzerinde:

```bash
java -jar target\smart-campus-announcement-system-1.0-SNAPSHOT.jar
```

## Ödev Gereksinimleri Kontrol Listesi

- [x] En az 2 kullanıcı tipi: Öğrenci, Öğretmen
- [x] En az 2 duyuru tipi: Sınav, Etkinlik
- [x] En az 2 bildirim tipi: Email, SMS, Push
- [x] Observer Pattern
- [x] Factory Pattern
- [x] Singleton Pattern
- [x] Katmanlı mimari
- [x] Interface veya abstract class
- [x] Basit veri yönetimi
- [x] Çalışan örnek senaryo
