package Lab5;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileIOService fileService = new FileIOService();

        System.out.println("=== Завдання 1: Пошук рядка з максимальною кількістю слів ===");
        System.out.print("Введіть шлях до текстового файлу: ");
        String filePath = scanner.nextLine();

        try {
            String result = fileService.findLineWithMaxWords(filePath);

            if (result != null) {
                int wordsCount = result.trim().split("\\s+").length;
                System.out.println("\nРезультат знайдено!");
                System.out.println("Кількість слів: " + wordsCount);
                System.out.println("Сам рядок: " + result);
            } else {
                System.out.println("\nФайл порожній або не містить жодного слова.");
            }

        } catch (IOException e) {
            System.err.println("Помилка під час роботи з файлом! Перевірте правильність шляху.");
            System.err.println("Технічна інформація: " + e.getMessage());
        }

        System.out.println("\n=== Завдання 3: Шифрування та Дешифрування ===");

        System.out.print("Введіть шлях до вхідного файлу для шифрування (наприклад, test.txt): ");
        String sourceFile = scanner.nextLine();

        System.out.print("Введіть шлях для ЗБЕРЕЖЕННЯ зашифрованого файлу (наприклад, enc.txt): ");
        String encryptedFile = scanner.nextLine();

        System.out.print("Введіть шлях для ЗБЕРЕЖЕННЯ розшифрованого файлу (наприклад, dec.txt): ");
        String decryptedFile = scanner.nextLine();

        System.out.print("Введіть ОДИН символ, який буде ключем шифрування (наприклад, K): ");
        char key = scanner.nextLine().charAt(0);

        try {
            fileService.encryptTextFile(sourceFile, encryptedFile, key);
            System.out.println("Файл успішно зашифровано у: " + encryptedFile);

            fileService.decryptTextFile(encryptedFile, decryptedFile, key);
            System.out.println("Файл успішно розшифровано у: " + decryptedFile);

        } catch (IOException e) {
            System.err.println("Помилка файлової системи! " + e.getMessage());
        }

        System.out.println("\n=== Завдання 4: Аналіз HTML тегів ===");
        System.out.print("Введіть URL сторінки (наприклад, https://example.com): ");
        String urlString = scanner.nextLine();

        Map<String, Integer> tagCounts = new HashMap<>();

        try {
            URL url = new URL(urlString);

            java.net.URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                Pattern pattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)[^>]*>");

                while ((line = in.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();
                        tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
                    }
                }
            }

            System.out.println("\n--- Теги за алфавітом ---");
            Map<String, Integer> sortedByName = new TreeMap<>(tagCounts);
            for (Map.Entry<String, Integer> entry : sortedByName.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            System.out.println("\n--- Теги за частотою появи ---");
            List<Map.Entry<String, Integer>> sortedByFreq = new ArrayList<>(tagCounts.entrySet());
            sortedByFreq.sort(Map.Entry.comparingByValue());
            for (Map.Entry<String, Integer> entry : sortedByFreq) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            System.out.print("\nВведіть шлях для ЗБЕРЕЖЕННЯ статистики (наприклад, tags.dat): ");
            String dataFile = scanner.nextLine();

            fileService.saveObject(tagCounts, dataFile);
            System.out.println("Дані успішно збережено!");

            System.out.print("Введіть назву тегу для пошуку його частоти (наприклад, div): ");
            String searchTag = scanner.nextLine().toLowerCase();

            @SuppressWarnings("unchecked")
            Map<String, Integer> loadedData = (Map<String, Integer>) fileService.readObject(dataFile);

            if (loadedData.containsKey(searchTag)) {
                System.out.println("Тег <" + searchTag + "> зустрічається " + loadedData.get(searchTag) + " разів.");
            } else {
                System.out.println("Тег <" + searchTag + "> не знайдено на цій сторінці.");
            }

        } catch (Exception e) {
            System.err.println("Мережева або системна помилка: " + e.getMessage());
        }
    }
}
