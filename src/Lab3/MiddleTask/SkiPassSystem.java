package Lab3.MiddleTask;

import java.util.HashMap;
import java.util.Map;

public class SkiPassSystem {
    // Реєстр карток: Ключ - унікальний ID, Значення - сам об'єкт SkiPass
    private Map<Integer, SkiPass> registry;

    public SkiPassSystem() {
        this.registry = new HashMap<>();
    }

    // 1. Випуск ski-pass по кількості підйомів (10, 20, 50, 100)
    public SkiPass issuePassByRides(PassType type, int rides) {
        SkiPass newPass = new SkiPass(type, rides);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено нову картку: " + newPass);
        return newPass;
    }

    // 2. Випуск ski-pass без обліку кількості поїздок (на час)
    public SkiPass issuePassByDuration(PassType type, PassDuration duration) {
        SkiPass newPass = new SkiPass(type, duration);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено нову картку: " + newPass);
        return newPass;
    }

    // Зручний метод для швидкого випуску абонемента на сезон
    public SkiPass issueSeasonPass() {
        SkiPass newPass = new SkiPass(PassType.SEASON, PassDuration.SEASON_DURATION);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено сезонний абонемент: " + newPass);
        return newPass;
    }

    // 3. Заблокувати ski-pass через порушення правил підйому
    public void blockPass(int id) {
        SkiPass pass = registry.get(id);
        if (pass != null) {
            pass.blockPass(); // Викликаємо метод блокування самої картки
            System.out.println("УВАГА: Картку #" + id + " примусово заблоковано за порушення!");
        } else {
            System.out.println("Помилка: Картку з ID #" + id + " не знайдено в реєстрі.");
        }
    }

    // Метод для турнікета: отримати картку з реєстру для перевірки
    public SkiPass getPass(int id) {
        return registry.get(id);
    }
}
