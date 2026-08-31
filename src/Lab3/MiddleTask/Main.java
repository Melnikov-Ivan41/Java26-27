package Lab3.MiddleTask;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК СИСТЕМИ ЛИЖНОГО КУРОРТУ ===");

        SkiPassSystem system = new SkiPassSystem();
        Turnstile turnstile = new Turnstile(system);

        System.out.println("\n--- Випуск карток (Робота каси) ---");
        SkiPass pass1 = system.issuePassByRides(PassType.WORKDAYS, 10);
        SkiPass pass2 = system.issuePassByDuration(PassType.WEEKENDS, PassDuration.ONE_DAY);
        SkiPass pass3 = system.issueSeasonPass();
        SkiPass pass4 = system.issuePassByRides(PassType.WORKDAYS, 1);

        System.out.println("\n--- Робота служби безпеки ---");
        system.blockPass(pass1.getId());

        System.out.println("\n--- Симуляція проходів через турнікет ---");

        turnstile.access(pass1.getId());

        turnstile.access(pass2.getId());

        turnstile.access(pass3.getId());

        turnstile.access(pass4.getId());

        turnstile.access(pass4.getId());

        turnstile.access(999);

        turnstile.printStatistics();
    }
}
