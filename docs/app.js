const users = [
  {
    name: "Ayşe Yılmaz",
    role: "Öğrenci",
    preference: "Email",
    observer: "StudentObserver",
    notificationClass: "EmailNotification",
    notificationText: "Email bildirimi"
  },
  {
    name: "Mehmet Kaya",
    role: "Öğrenci",
    preference: "SMS",
    observer: "StudentObserver",
    notificationClass: "SmsNotification",
    notificationText: "SMS bildirimi"
  },
  {
    name: "Dr. Elif Demir",
    role: "Öğretmen",
    preference: "Push",
    observer: "TeacherObserver",
    notificationClass: "PushNotification",
    notificationText: "Push bildirimi"
  }
];

const announcementTypes = {
  EXAM: {
    label: "Sınav Duyurusu",
    className: "ExamAnnouncement",
    title: "Ara Sınav Programı",
    message: "Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır."
  },
  EVENT: {
    label: "Etkinlik Duyurusu",
    className: "EventAnnouncement",
    title: "Kariyer Günleri Etkinliği",
    message: "Teknoloji firmalarının katılacağı kariyer etkinliği çarşamba günü konferans salonunda yapılacaktır."
  },
  FOOD_MENU: {
    label: "Yemekhane Duyurusu",
    className: "FoodMenuAnnouncement",
    title: "Haftalık Yemekhane Menüsü",
    message: "Bu haftanın öğle menüsü öğrenci işleri panosunda ve kampüs portalında yayınlanmıştır."
  },
  LIBRARY: {
    label: "Kütüphane Duyurusu",
    className: "LibraryAnnouncement",
    title: "Kütüphane Çalışma Saatleri",
    message: "Final haftası boyunca merkez kütüphane hafta içi 22:00'ye kadar açık olacaktır."
  }
};

const demoScenarioSteps = [
  { tag: "[Domain Layer]", kind: "domain", message: "Kullanıcılar sisteme eklendi." },
  { tag: "[Domain Layer]", kind: "domain", message: "Bildirim tercihleri belirlendi." },
  { tag: "[Application Layer]", kind: "application", message: "Yönetici yeni bir sınav duyurusu oluşturdu." },
  { tag: "[Factory]", kind: "factory", message: "AnnouncementFactory uygun duyuru nesnesini oluşturdu." },
  { tag: "[Application Layer]", kind: "application", message: "AnnouncementPublisher duyuruyu yayınladı." },
  { tag: "[Observer]", kind: "observer", message: "Observer yapısı ile öğrenci ve öğretmenler bilgilendirildi." },
  { tag: "[Factory]", kind: "factory", message: "NotificationFactory uygun bildirim kanallarını oluşturdu." },
  { tag: "[Notification]", kind: "notification", message: "Email/SMS/Push bildirimleri gönderildi." },
  { tag: "[Infrastructure Layer]", kind: "infrastructure", message: "InMemoryUserRepository basit veri yönetimini temsil etti." },
  { tag: "[Singleton]", kind: "singleton", message: "Logger Singleton kayıt aldı." }
];

const userList = document.querySelector("#userList");
const form = document.querySelector("#announcementForm");
const typeSelect = document.querySelector("#announcementType");
const titleInput = document.querySelector("#announcementTitle");
const messageInput = document.querySelector("#announcementMessage");
const logOutput = document.querySelector("#logOutput");
const clearLogsButton = document.querySelector("#clearLogs");
const publishButton = document.querySelector("#publishButton");
const scenarioButton = document.querySelector("#scenarioButton");

function renderUsers() {
  userList.innerHTML = "";

  users.forEach((user) => {
    const card = document.createElement("article");
    card.className = "user-card";

    const avatar = document.createElement("div");
    avatar.className = "avatar";
    avatar.textContent = getInitials(user.name);

    const info = document.createElement("div");
    info.className = "user-info";

    const name = document.createElement("h2");
    name.textContent = user.name;

    const detail = document.createElement("p");
    detail.textContent = `${user.role} - ${user.preference}`;

    const observer = document.createElement("p");
    observer.textContent = user.observer;

    info.append(name, detail, observer);
    card.append(avatar, info);
    userList.appendChild(card);
  });
}

function getInitials(name) {
  return name
    .split(" ")
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part[0])
    .join("")
    .toUpperCase();
}

function addLog(tag, message, kind = "system") {
  const line = document.createElement("div");
  line.className = "log-line";
  line.dataset.kind = kind;

  const time = document.createElement("span");
  time.className = "log-time";
  time.textContent = new Date().toLocaleTimeString("tr-TR", {
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit"
  });

  const tagElement = document.createElement("span");
  tagElement.className = "log-tag";
  tagElement.textContent = tag;

  const text = document.createElement("span");
  text.className = "log-message";
  text.textContent = message;

  line.append(time, tagElement, text);
  logOutput.appendChild(line);
  logOutput.scrollTop = logOutput.scrollHeight;
}

function runSteps(steps) {
  publishButton.disabled = true;
  scenarioButton.disabled = true;

  steps.forEach((step, index) => {
    window.setTimeout(() => {
      addLog(step.tag, step.message, step.kind);

      if (index === steps.length - 1) {
        publishButton.disabled = false;
        scenarioButton.disabled = false;
      }
    }, index * 260);
  });
}

function buildAnnouncementSteps(typeKey, title, message) {
  const type = announcementTypes[typeKey];
  const steps = [
    {
      tag: "[Application Layer]",
      kind: "application",
      message: `Yönetici "${title}" başlıklı ${type.label} oluşturma isteği verdi.`
    },
    {
      tag: "[Factory]",
      kind: "factory",
      message: `AnnouncementFactory uygun duyuru nesnesini oluşturdu: ${type.className}.`
    },
    {
      tag: "[Application Layer]",
      kind: "application",
      message: `AnnouncementPublisher duyuruyu yayınladı: ${title}.`
    },
    {
      tag: "[Observer]",
      kind: "observer",
      message: "Observer yapısı kayıtlı öğrenci ve öğretmenleri dolaşmaya başladı."
    }
  ];

  users.forEach((user) => {
    steps.push(
      {
        tag: "[Observer]",
        kind: "observer",
        message: `${user.observer} bilgilendirildi: ${user.name}.`
      },
      {
        tag: "[Factory]",
        kind: "factory",
        message: `NotificationFactory ${user.preference} tercihi için ${user.notificationClass} oluşturdu.`
      },
      {
        tag: "[Notification]",
        kind: "notification",
        message: `${user.notificationText} gönderildi -> ${user.name}: ${title} - ${message}`
      }
    );
  });

  steps.push(
    {
      tag: "[Infrastructure Layer]",
      kind: "infrastructure",
      message: "Logger altyapı katmanında kayıt işlemini hazırladı."
    },
    {
      tag: "[Singleton]",
      kind: "singleton",
      message: `Logger Singleton kayıt aldı: ${typeKey} - ${title}.`
    }
  );

  return steps;
}

function applyTypeDefaults() {
  const selected = announcementTypes[typeSelect.value];
  titleInput.value = selected.title;
  messageInput.value = selected.message;
}

form.addEventListener("submit", (event) => {
  event.preventDefault();

  const typeKey = typeSelect.value;
  const selected = announcementTypes[typeKey];
  const title = titleInput.value.trim() || selected.title;
  const message = messageInput.value.trim() || selected.message;

  titleInput.value = title;
  messageInput.value = message;

  runSteps(buildAnnouncementSteps(typeKey, title, message));
});

scenarioButton.addEventListener("click", () => {
  runSteps(demoScenarioSteps);
});

typeSelect.addEventListener("change", applyTypeDefaults);

clearLogsButton.addEventListener("click", () => {
  logOutput.innerHTML = "";
  addLog("[Application Layer]", "Sistem hazır. Duyuru yayınlamak için formu kullanın veya demo senaryosunu çalıştırın.", "application");
});

renderUsers();
addLog("[Application Layer]", "Sistem hazır. Duyuru yayınlamak için formu kullanın veya demo senaryosunu çalıştırın.", "application");
