package Lab3.MiddleTask;

import java.util.HashMap;
import java.util.Map;

public class SkiPassSystem {
    private Map<Integer, SkiPass> registry;

    public SkiPassSystem() {
        this.registry = new HashMap<>();
    }


    public SkiPass issuePassByRides(PassType type, int rides) {
        SkiPass newPass = new SkiPass(type, rides);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено нову картку: " + newPass);
        return newPass;
    }


    public SkiPass issuePassByDuration(PassType type, PassDuration duration) {
        SkiPass newPass = new SkiPass(type, duration);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено нову картку: " + newPass);
        return newPass;
    }


    public SkiPass issueSeasonPass() {
        SkiPass newPass = new SkiPass(PassType.SEASON, PassDuration.SEASON_DURATION);
        registry.put(newPass.getId(), newPass);
        System.out.println("Випущено сезонний абонемент: " + newPass);
        return newPass;
    }


    public void blockPass(int id) {
        SkiPass pass = registry.get(id);
        if (pass != null) {
            pass.blockPass();
            System.out.println("УВАГА: Картку #" + id + " примусово заблоковано за порушення!");
        } else {
            System.out.println("Помилка: Картку з ID #" + id + " не знайдено в реєстрі.");
        }
    }

    public SkiPass getPass(int id) {
        return registry.get(id);
    }
}
