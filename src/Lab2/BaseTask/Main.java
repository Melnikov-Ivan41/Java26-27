package Lab2.BaseTask;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        // a. Створюємо екземпляр Person
        Person originalPerson = new Person("Шевченко", "Тарас", 47);

        // b. Конвертуємо в JSON за допомогою бібліотеки Gson
        Gson gson = new Gson();
        String jsonString = gson.toJson(originalPerson);
        System.out.println("JSON рядок: " + jsonString);

        // c. Конвертуємо назад в об'єкт
        Person deserializedPerson = gson.fromJson(jsonString, Person.class);
        System.out.println("Відновлений об'єкт: " + deserializedPerson);

        // d. Перевіряємо equals-ом початковий і одержаний об'єкти
        boolean areEqual = originalPerson.equals(deserializedPerson);
        System.out.println("Чи рівні об'єкти (equals): " + areEqual);
    }
}
