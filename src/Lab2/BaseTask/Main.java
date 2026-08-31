package Lab2.BaseTask;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Person originalPerson = new Person("Шевченко", "Тарас", 47);

        Gson gson = new Gson();
        String jsonString = gson.toJson(originalPerson);
        System.out.println("JSON рядок: " + jsonString);

        Person deserializedPerson = gson.fromJson(jsonString, Person.class);
        System.out.println("Відновлений об'єкт: " + deserializedPerson);

        boolean areEqual = originalPerson.equals(deserializedPerson);
        System.out.println("Чи рівні об'єкти (equals): " + areEqual);
    }
}
