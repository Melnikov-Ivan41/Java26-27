package Lab2.Base2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<JournalEntry> journal = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Журнал куратора ===");

        while (true) {
            System.out.println("\nОберіть дію: 1 - Додати запис, 2 - Показати журнал, 0 - Вийти");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    showJournal();
                    break;
                case "0":
                    System.out.println("Роботу завершено.");
                    return;
                default:
                    System.out.println("Помилка: невідома команда.");
            }
        }
    }

    private static void addEntry() {
        System.out.println("\n--- Створення нового запису ---");

        String nameRegex = "^[А-ЯІЇЄҐ][а-яіїєґ']+(-[А-ЯІЇЄҐ][а-яіїєґ']+)?$";
        String phoneRegex = "^\\+380\\d{9}$";

        String surname = readValidString("Прізвище (з великої літери): ", nameRegex, "Некоректне прізвище. Використовуйте кирилицю.");
        String name = readValidString("Ім'я (з великої літери): ", nameRegex, "Некоректне ім'я. Використовуйте кирилицю.");
        LocalDate birthDate = readValidDate("Дата народження (дд.мм.рррр): ");
        String phone = readValidString("Телефон (у форматі +380XXXXXXXXX): ", phoneRegex, "Некоректний формат телефону.");

        System.out.println("--- Домашня адреса ---");
        String street = readValidString("Вулиця: ", "^[А-ЯІЇЄҐа-яіїєґ0-9\\s\\-.]+$", "Некоректна назва вулиці.");
        String building = readValidString("Будинок (наприклад, 12 або 15А): ", "^[1-9]\\d*[А-ЯІЇЄҐа-яіїєґ]?$", "Некоректний номер будинку.");
        int apartment = readValidInt("Квартира: ");

        Address address = new Address(street, building, apartment);
        JournalEntry entry = new JournalEntry(surname, name, birthDate, phone, address);

        journal.add(entry);
        System.out.println("Запис успішно додано!");
    }

    private static void showJournal() {
        System.out.println("\n--- Всі записи журналу ---");
        if (journal.isEmpty()) {
            System.out.println("Журнал порожній.");
            return;
        }
        for (int i = 0; i < journal.size(); i++) {
            System.out.println((i + 1) + ". " + journal.get(i));
        }
    }

    private static String readValidString(String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (Pattern.matches(regex, input)) {
                return input;
            }
            System.out.println("Помилка: " + errorMessage);
        }
    }

    private static LocalDate readValidDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);

                // Перевірка, щоб дата не була в майбутньому
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Помилка: Дата народження не може бути в майбутньому.");
                    continue; // Повертаємось на початок циклу
                }

                // Можна також додати перевірку на адекватний вік (наприклад, не старше 100 років)
                if (date.isBefore(LocalDate.now().minusYears(120))) {
                    System.out.println("Помилка: Введена дата занадто стара.");
                    continue;
                }

                return date; // Якщо всі перевірки пройдені, повертаємо дату
            } catch (DateTimeParseException e) {
                System.out.println("Помилка: Введіть дату в точному форматі дд.мм.рррр (наприклад, 05.09.2003).");
            }
        }
    }

    private static int readValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Помилка: Номер квартири має бути більшим за 0.");
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть ціле число.");
            }
        }
    }
}
