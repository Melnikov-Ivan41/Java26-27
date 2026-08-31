package Lab3.MiddleTask;

import java.time.LocalDateTime;

public class SkiPass {
    private static int idCounter = 1;

    private final int id;
    private final PassType passType;
    private final PassLimit passLimit;
    private final PassDuration passDuration;

    private int ridesLeft;
    private boolean isBlocked;
    private final LocalDateTime issueDate;

    public SkiPass(PassType passType, int totalRides) {
        this.id = idCounter++;
        this.passType = passType;
        this.passLimit = PassLimit.BY_RIDES;
        this.passDuration = PassDuration.NOT_APPLICABLE;
        this.ridesLeft = totalRides;
        this.isBlocked = false;
        this.issueDate = LocalDateTime.now();
    }

    public SkiPass(PassType passType, PassDuration passDuration) {
        this.id = idCounter++;
        this.passType = passType;
        this.passLimit = PassLimit.UNLIMITED_RIDES;
        this.passDuration = passDuration;
        this.ridesLeft = -1;
        this.isBlocked = false;
        this.issueDate = LocalDateTime.now();
    }

    public void blockPass() {
        this.isBlocked = true;
    }

    public void deductRide() {
        if (passLimit == PassLimit.BY_RIDES && ridesLeft > 0) {
            ridesLeft--;
        }
    }

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
