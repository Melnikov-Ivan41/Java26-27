package Lab3.MiddleTask;

import java.time.LocalDateTime;

public class SkiPass {
    // Статическая переменная для генерации уникальных ID
    private static int idCounter = 1;

    private final int id;
    private final PassType passType;
    private final PassLimit passLimit;
    private final PassDuration passDuration;

    private int ridesLeft;       // Оставшееся количество поездок
    private boolean isBlocked;   // Статус блокировки
    private final LocalDateTime issueDate; // Дата и время выпуска карты

    // Конструктор №1: Для карт с лимитом по количеству подъемов (10, 20, 50, 100)[cite: 1]
    public SkiPass(PassType passType, int totalRides) {
        this.id = idCounter++;
        this.passType = passType;
        this.passLimit = PassLimit.BY_RIDES;
        this.passDuration = PassDuration.NOT_APPLICABLE;
        this.ridesLeft = totalRides;
        this.isBlocked = false;
        this.issueDate = LocalDateTime.now(); // Фиксируем время выдачи
    }

    // Конструктор №2: Для безлимитных карт по времени (полдня, день, сезон)[cite: 1]
    public SkiPass(PassType passType, PassDuration passDuration) {
        this.id = idCounter++;
        this.passType = passType;
        this.passLimit = PassLimit.UNLIMITED_RIDES;
        this.passDuration = passDuration;
        this.ridesLeft = -1; // -1 будет означать "безлимит"
        this.isBlocked = false;
        this.issueDate = LocalDateTime.now();
    }

    // --- Бизнес-методы карты ---

    // Метод блокировки карты (за нарушение правил)[cite: 1]
    public void blockPass() {
        this.isBlocked = true;
    }

    // Списание одной поездки (вызывается турникетом)[cite: 1]
    public void deductRide() {
        if (passLimit == PassLimit.BY_RIDES && ridesLeft > 0) {
            ridesLeft--;
        }
    }

    // --- Геттеры для Турникета ---

    public int getId() { return id; }
    public PassType getPassType() { return passType; }
    public PassLimit getPassLimit() { return passLimit; }
    public PassDuration getPassDuration() { return passDuration; }
    public int getRidesLeft() { return ridesLeft; }
    public boolean isBlocked() { return isBlocked; }
    public LocalDateTime getIssueDate() { return issueDate; }

    @Override
    public String toString() {
        return String.format("SkiPass #%d [Тип: %s, Лимит: %s, Длительность: %s, Поездок осталось: %d, Заблокирован: %b]",
                id, passType, passLimit, passDuration, ridesLeft, isBlocked);
    }
}
