package Lab3.MiddleTask;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Turnstile {
    private final SkiPassSystem system;

    // Змінні для збору статистики
    private int totalGranted = 0;
    private int totalDenied = 0;

    // Статистика розбита по типах карток
    private final Map<PassType, Integer> grantedByType = new HashMap<>();
    private final Map<PassType, Integer> deniedByType = new HashMap<>();

    // Конструктор приймає систему карток, щоб турнікет міг звіряти дані
    public Turnstile(SkiPassSystem system) {
        this.system = system;
        // Ініціалізуємо мапи нулями
        for (PassType type : PassType.values()) {
            grantedByType.put(type, 0);
            deniedByType.put(type, 0);
        }
    }

    // Головний метод перевірки (імітація прикладання картки)
    public boolean access(int passId) {
        System.out.println("\n--- Зчитування картки #" + passId + " ---");
        SkiPass pass = system.getPass(passId);

        // 1. Картку не знайдено (не вдалося зчитати дані)
        if (pass == null) {
            System.out.println("ВІДМОВА: Картку не розпізнано системою.");
            totalDenied++;
            return false;
        }

        // 2. Картка заблокована
        if (pass.isBlocked()) {
            System.out.println("ВІДМОВА: Картку заблоковано!");
            recordDenial(pass.getPassType());
            return false;
        }

        // 3. Не залишилося кредитів (для карток на кількість поїздок)
        if (pass.getPassLimit() == PassLimit.BY_RIDES && pass.getRidesLeft() <= 0) {
            System.out.println("ВІДМОВА: Не залишилося поїздок.");
            recordDenial(pass.getPassType());
            return false;
        }

        // 4. Перевірка на відповідність типу дня (Будні чи Вихідні)
        if (!isValidDayType(pass)) {
            System.out.println("ВІДМОВА: Картка не діє в цей день тижня.");
            recordDenial(pass.getPassType());
            return false;
        }

        // 5. Перевірка терміну дії (для карток на час)
        if (pass.getPassLimit() == PassLimit.UNLIMITED_RIDES && !isDurationValid(pass)) {
            System.out.println("ВІДМОВА: Термін дії картки минув.");
            recordDenial(pass.getPassType());
            return false;
        }

        // Усі перевірки пройдено — дозволяємо прохід
        pass.deductRide(); // Знімаємо поїздку (якщо треба)
        System.out.println("ПРОХІД ДОЗВОЛЕНО. Гарного катання!");
        recordGrant(pass.getPassType());

        return true;
    }

    // --- Допоміжні методи валідації ---

    private boolean isValidDayType(SkiPass pass) {
        if (pass.getPassType() == PassType.SEASON) return true; // Сезонна діє завжди

        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        boolean isWeekend = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);

        if (pass.getPassType() == PassType.WORKDAYS && isWeekend) return false;
        if (pass.getPassType() == PassType.WEEKENDS && !isWeekend) return false;

        return true;
    }

    private boolean isDurationValid(SkiPass pass) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime issueTime = pass.getIssueDate();

        // Спрощена логіка перевірки часу для прикладу
        switch (pass.getPassDuration()) {
            case HALF_DAY_MORNING: return now.getHour() >= 9 && now.getHour() < 13;
            case HALF_DAY_AFTERNOON: return now.getHour() >= 13 && now.getHour() < 17;
            case ONE_DAY: return now.isBefore(issueTime.plusDays(1));
            case TWO_DAYS: return now.isBefore(issueTime.plusDays(2));
            case FIVE_DAYS: return now.isBefore(issueTime.plusDays(5));
            case SEASON_DURATION: return true; // Завжди валідна в межах сезону
            default: return true;
        }
    }

    // --- Методи обліку статистики ---

    private void recordGrant(PassType type) {
        totalGranted++;
        grantedByType.put(type, grantedByType.get(type) + 1);
    }

    private void recordDenial(PassType type) {
        totalDenied++;
        deniedByType.put(type, deniedByType.get(type) + 1);
    }

    // Метод для видачі сумарних даних та даних по типах
    public void printStatistics() {
        System.out.println("\n========== СТАТИСТИКА ТУРНІКЕТА ==========");
        System.out.println("Загальна кількість дозволів: " + totalGranted);
        System.out.println("Загальна кількість відмов: " + totalDenied);

        System.out.println("\nРозбивка дозволів за типами:");
        for (PassType type : PassType.values()) {
            System.out.println(" - " + type + ": " + grantedByType.get(type));
        }

        System.out.println("\nРозбивка відмов за типами:");
        for (PassType type : PassType.values()) {
            System.out.println(" - " + type + ": " + deniedByType.get(type));
        }
        System.out.println("==========================================");
    }
}
