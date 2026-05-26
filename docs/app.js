const STORAGE_KEY = "smartCampusAppState";

const defaultUsers = [
  {
    id: "user-ayse",
    name: "Ayşe Yılmaz",
    role: "Öğrenci",
    preference: "Email"
  },
  {
    id: "user-mehmet",
    name: "Mehmet Kaya",
    role: "Öğrenci",
    preference: "SMS"
  },
  {
    id: "user-elif",
    name: "Dr. Elif Demir",
    role: "Öğretmen",
    preference: "Push"
  }
];

const announcementTypes = {
  EXAM: {
    label: "Sınav",
    className: "ExamAnnouncement",
    defaultTitle: "Ara Sınav Programı",
    defaultMessage: "Yazılım Mimarisi sınavı cuma günü saat 10:00'da B-204 salonunda yapılacaktır."
  },
  EVENT: {
    label: "Etkinlik",
    className: "EventAnnouncement",
    defaultTitle: "Kariyer Günleri Etkinliği",
    defaultMessage: "Teknoloji firmalarının katılacağı kariyer etkinliği çarşamba günü konferans salonunda yapılacaktır."
  },
  FOOD_MENU: {
    label: "Yemekhane",
    className: "FoodMenuAnnouncement",
    defaultTitle: "Haftalık Yemekhane Menüsü",
    defaultMessage: "Bu haftanın öğle menüsü öğrenci işleri panosunda ve kampüs portalında yayınlanmıştır."
  },
  LIBRARY: {
    label: "Kütüphane",
    className: "LibraryAnnouncement",
    defaultTitle: "Kütüphane Çalışma Saatleri",
    defaultMessage: "Final haftası boyunca merkez kütüphane hafta içi 22:00'ye kadar açık olacaktır."
  }
};

let state = loadState();

const elements = {
  feedback: document.querySelector("#feedback"),
  userCount: document.querySelector("#userCount"),
  announcementCount: document.querySelector("#announcementCount"),
  notificationCount: document.querySelector("#notificationCount"),
  logCount: document.querySelector("#logCount"),
  userForm: document.querySelector("#userForm"),
  userName: document.querySelector("#userName"),
  userRole: document.querySelector("#userRole"),
  userPreference: document.querySelector("#userPreference"),
  userList: document.querySelector("#userList"),
  announcementForm: document.querySelector("#announcementForm"),
  announcementType: document.querySelector("#announcementType"),
  announcementTitle: document.querySelector("#announcementTitle"),
  announcementMessage: document.querySelector("#announcementMessage"),
  announcementList: document.querySelector("#announcementList"),
  notificationList: document.querySelector("#notificationList"),
  logOutput: document.querySelector("#logOutput"),
  resetDataButton: document.querySelector("#resetDataButton")
};

function createInitialState() {
  return {
    users: defaultUsers.map((user) => ({ ...user })),
    announcements: [],
    notifications: [],
    logs: [
      createLog("[Application Layer]", "Sistem hazır. Kullanıcı ekleyebilir veya duyuru yayınlayabilirsiniz.", "application")
    ]
  };
}

function loadState() {
  const stored = localStorage.getItem(STORAGE_KEY);

  if (!stored) {
    return createInitialState();
  }

  try {
    const parsed = JSON.parse(stored);
    return {
      users: Array.isArray(parsed.users) ? parsed.users : defaultUsers.map((user) => ({ ...user })),
      announcements: Array.isArray(parsed.announcements) ? parsed.announcements : [],
      notifications: Array.isArray(parsed.notifications) ? parsed.notifications : [],
      logs: Array.isArray(parsed.logs) ? parsed.logs : []
    };
  } catch (error) {
    return createInitialState();
  }
}

function saveState() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
}

function createId(prefix) {
  return `${prefix}-${Date.now()}-${Math.random().toString(16).slice(2)}`;
}

function getTime() {
  return new Date().toLocaleTimeString("tr-TR", {
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit"
  });
}

function createLog(tag, message, kind) {
  return {
    id: createId("log"),
    time: getTime(),
    tag,
    message,
    kind
  };
}

function pushLog(tag, message, kind) {
  state.logs.push(createLog(tag, message, kind));
}

function showFeedback(message, type = "info") {
  elements.feedback.textContent = message;
  elements.feedback.classList.toggle("error", type === "error");

  window.setTimeout(() => {
    if (elements.feedback.textContent === message) {
      elements.feedback.textContent = "";
      elements.feedback.classList.remove("error");
    }
  }, 3200);
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

function getObserverName(role) {
  return role === "Öğretmen" ? "TeacherObserver" : "StudentObserver";
}

function render() {
  renderStats();
  renderUsers();
  renderAnnouncements();
  renderNotifications();
  renderLogs();
  saveState();
}

function renderStats() {
  elements.userCount.textContent = state.users.length;
  elements.announcementCount.textContent = state.announcements.length;
  elements.notificationCount.textContent = state.notifications.length;
  elements.logCount.textContent = state.logs.length;
}

function renderUsers() {
  elements.userList.innerHTML = "";

  if (state.users.length === 0) {
    elements.userList.appendChild(createEmptyState("Henüz kullanıcı yok. Duyuru yayınlamak için önce kullanıcı ekleyin."));
    return;
  }

  state.users.forEach((user) => {
    const card = document.createElement("article");
    card.className = "user-card";

    const avatar = document.createElement("div");
    avatar.className = "avatar";
    avatar.textContent = getInitials(user.name);

    const info = document.createElement("div");
    info.className = "user-info";

    const name = document.createElement("h2");
    name.textContent = user.name;

    const details = document.createElement("p");
    details.textContent = `${user.role} - ${user.preference}`;

    const observer = document.createElement("p");
    observer.textContent = getObserverName(user.role);

    const deleteButton = document.createElement("button");
    deleteButton.className = "delete-button";
    deleteButton.type = "button";
    deleteButton.textContent = "Sil";
    deleteButton.addEventListener("click", () => deleteUser(user.id));

    info.append(name, details, observer);
    card.append(avatar, info, deleteButton);
    elements.userList.appendChild(card);
  });
}

function renderAnnouncements() {
  elements.announcementList.innerHTML = "";

  if (state.announcements.length === 0) {
    elements.announcementList.appendChild(createEmptyState("Henüz yayınlanan duyuru yok."));
    return;
  }

  state.announcements
    .slice()
    .reverse()
    .forEach((announcement) => {
      const type = announcementTypes[announcement.type];
      const card = document.createElement("article");
      card.className = "record-card";

      const title = document.createElement("h3");
      title.textContent = announcement.title;

      const message = document.createElement("p");
      message.textContent = announcement.message;

      const meta = document.createElement("div");
      meta.className = "record-meta";
      meta.append(
        createTag(type.label),
        createTag(type.className),
        createTag(announcement.time)
      );

      card.append(title, message, meta);
      elements.announcementList.appendChild(card);
    });
}

function renderNotifications() {
  elements.notificationList.innerHTML = "";

  if (state.notifications.length === 0) {
    elements.notificationList.appendChild(createEmptyState("Henüz bildirim kaydı yok."));
    return;
  }

  state.notifications
    .slice()
    .reverse()
    .forEach((notification) => {
      const card = document.createElement("article");
      card.className = "record-card";

      const title = document.createElement("h3");
      title.textContent = `${notification.userName} kullanıcısına ${notification.channel} bildirimi gönderildi.`;

      const message = document.createElement("p");
      message.textContent = notification.announcementTitle;

      const meta = document.createElement("div");
      meta.className = "record-meta";
      meta.append(
        createTag(notification.userRole),
        createTag(notification.channel),
        createTag(notification.time)
      );

      card.append(title, message, meta);
      elements.notificationList.appendChild(card);
    });
}

function renderLogs() {
  elements.logOutput.innerHTML = "";

  if (state.logs.length === 0) {
    elements.logOutput.appendChild(createEmptyState("Henüz sistem logu yok."));
    return;
  }

  state.logs
    .slice(-80)
    .forEach((log) => {
      const line = document.createElement("div");
      line.className = "log-line";
      line.dataset.kind = log.kind;

      const time = document.createElement("span");
      time.className = "log-time";
      time.textContent = log.time;

      const tag = document.createElement("span");
      tag.className = "log-tag";
      tag.textContent = log.tag;

      const message = document.createElement("span");
      message.className = "log-message";
      message.textContent = log.message;

      line.append(time, tag, message);
      elements.logOutput.appendChild(line);
    });

  elements.logOutput.scrollTop = elements.logOutput.scrollHeight;
}

function createTag(text) {
  const tag = document.createElement("span");
  tag.className = "tag";
  tag.textContent = text;
  return tag;
}

function createEmptyState(message) {
  const empty = document.createElement("div");
  empty.className = "empty-state";
  empty.textContent = message;
  return empty;
}

function addUser(event) {
  event.preventDefault();

  const name = elements.userName.value.trim();
  const role = elements.userRole.value;
  const preference = elements.userPreference.value;

  if (!name) {
    showFeedback("Ad Soyad alanı boş bırakılamaz.", "error");
    elements.userName.focus();
    return;
  }

  const user = {
    id: createId("user"),
    name,
    role,
    preference
  };

  state.users.push(user);
  pushLog("[Domain Layer]", `${user.name} sisteme ${user.role} kullanıcısı olarak eklendi.`, "system");
  showFeedback(`${user.name} kullanıcı listesine eklendi.`);
  elements.userForm.reset();
  render();
}

function deleteUser(userId) {
  const user = state.users.find((item) => item.id === userId);
  state.users = state.users.filter((item) => item.id !== userId);

  if (user) {
    pushLog("[Domain Layer]", `${user.name} kullanıcı listesinden silindi.`, "system");
    showFeedback(`${user.name} silindi.`);
  }

  render();
}

function publishAnnouncement(event) {
  event.preventDefault();

  const typeKey = elements.announcementType.value;
  const type = announcementTypes[typeKey];
  const title = elements.announcementTitle.value.trim();
  const message = elements.announcementMessage.value.trim();

  if (!title) {
    showFeedback("Duyuru başlığı boş bırakılamaz.", "error");
    elements.announcementTitle.focus();
    return;
  }

  if (!message) {
    showFeedback("Duyuru mesajı boş bırakılamaz.", "error");
    elements.announcementMessage.focus();
    return;
  }

  if (state.users.length === 0) {
    showFeedback("Duyuru yayınlamak için en az bir kullanıcı ekleyin.", "error");
    return;
  }

  const announcement = {
    id: createId("announcement"),
    type: typeKey,
    title,
    message,
    time: getTime()
  };

  state.announcements.push(announcement);
  addPublishLogs(type);

  state.users.forEach((user) => {
    state.notifications.push({
      id: createId("notification"),
      userId: user.id,
      userName: user.name,
      userRole: user.role,
      channel: user.preference,
      announcementId: announcement.id,
      announcementTitle: announcement.title,
      time: getTime()
    });
  });

  showFeedback(`${announcement.title} yayınlandı ve ${state.users.length} kullanıcıya bildirim oluşturuldu.`);
  render();
}

function addPublishLogs(type) {
  pushLog("[Factory]", `AnnouncementFactory uygun duyuru nesnesini oluşturdu: ${type.className}.`, "factory");
  pushLog("[Application Layer]", "AnnouncementService yayınlama işlemini başlattı.", "application");
  pushLog("[Observer]", "AnnouncementPublisher duyuruyu yayınladı.", "observer");
  pushLog("[Observer]", "StudentObserver ve TeacherObserver kullanıcıları bilgilendirdi.", "observer");
  pushLog("[Factory]", "NotificationFactory kullanıcı tercihlerine göre bildirim nesnelerini oluşturdu.", "factory");
  pushLog("[Notification]", "Email/SMS/Push bildirimleri gönderildi.", "notification");
  pushLog("[Singleton]", "Logger kayıt aldı.", "singleton");
}

function resetData() {
  localStorage.removeItem(STORAGE_KEY);
  state = createInitialState();
  applyTypeDefaults();
  showFeedback("Veriler sıfırlandı ve varsayılan kullanıcılar yeniden yüklendi.");
  render();
}

function applyTypeDefaults() {
  const selected = announcementTypes[elements.announcementType.value];
  elements.announcementTitle.value = selected.defaultTitle;
  elements.announcementMessage.value = selected.defaultMessage;
}

elements.userForm.addEventListener("submit", addUser);
elements.announcementForm.addEventListener("submit", publishAnnouncement);
elements.resetDataButton.addEventListener("click", resetData);
elements.announcementType.addEventListener("change", applyTypeDefaults);

render();
