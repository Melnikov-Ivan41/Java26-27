package Lab3.MiddleTask;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК СИСТЕМИ ЛИЖНОГО КУРОРТУ ===");

        // 1. Ініціалізуємо систему реєстру та сам турнікет
        SkiPassSystem system = new SkiPassSystem();
        Turnstile turnstile = new Turnstile(system);

        System.out.println("\n--- Випуск карток (Робота каси) ---");
        // Випускаємо різні типи карток для тестування
        SkiPass pass1 = system.issuePassByRides(PassType.WORKDAYS, 10); // На 10 підйомів у будні
        SkiPass pass2 = system.issuePassByDuration(PassType.WEEKENDS, PassDuration.ONE_DAY); // На 1 день у вихідні
        SkiPass pass3 = system.issueSeasonPass(); // Сезонний абонемент
        SkiPass pass4 = system.issuePassByRides(PassType.WORKDAYS, 1); // Картка лише на 1 підйом (щоб перевірити ліміт)

        System.out.println("\n--- Робота служби безпеки ---");
        // Блокуємо першу картку за порушення правил
        system.blockPass(pass1.getId());

        System.out.println("\n--- Симуляція проходів через турнікет ---");

        // Тест 1: Спроба пройти по заблокованій картці
        turnstile.access(pass1.getId());

        // Тест 2: Спроба пройти з карткою вихідного дня (поведінка залежатиме від того, в який день ти запустиш код)
        turnstile.access(pass2.getId());

        // Тест 3: Прохід по сезонному абонементу (має пустити завжди)
        turnstile.access(pass3.getId());

        // Тест 4: Прохід по картці з 1 поїздкою (має пустити і списати поїздку)
        // (Примітка: якщо ти запустиш це у вихідний, система відмовить, бо картка для будніх)
        turnstile.access(pass4.getId());

        // Тест 5: Повторна спроба пройти по тій самій картці (поїздки вже закінчилися)
        turnstile.access(pass4.getId());

        // Тест 6: Хтось приклав фальшиву картку (з неіснуючим ID)
        turnstile.access(999);

        // 3. Виведення фінальної статистики
        turnstile.printStatistics();
    }
}
