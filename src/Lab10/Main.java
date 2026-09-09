package Lab10;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setupLogger();
        logger.info("Програму запущено.");

        Scanner scanner = new Scanner(System.in);
        FileIOService fileService = new FileIOService();

        System.out.print("Оберіть мову / Choose language (1 - Українська, 2 - English): ");
        String langChoice = scanner.nextLine();

        Locale locale;
        if (langChoice.equals("2")) {
            locale = new Locale("en", "US");
            logger.fine("Користувач обрав англійську мову.");
        } else {
            locale = new Locale("uk", "UA");
            logger.fine("Користувач обрав українську мову.");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("location.messages", locale);

        System.out.println("\n" + bundle.getString("task1.title"));
        System.out.print(bundle.getString("task1.prompt"));
        String filePath = scanner.nextLine();
        logger.fine("Початок виконання Завдання 1. Введений шлях: " + filePath);

        try {
            String result = fileService.findLineWithMaxWords(filePath);
            if (result != null) {
                int wordsCount = result.trim().split("\\s+").length;
                System.out.println("\n" + bundle.getString("task1.success"));
                System.out.println(bundle.getString("task1.words") + wordsCount);
                System.out.println(bundle.getString("task1.line") + result);
                logger.info("Завдання 1 виконано успішно.");
            } else {
                System.out.println("\n" + bundle.getString("task1.empty"));
                logger.warning("Завдання 1: Файл порожній.");
            }
        } catch (IOException e) {
            System.err.println(bundle.getString("error.file"));
            logger.log(Level.SEVERE, "Помилка читання файлу", e);
        }

        System.out.println("\n" + bundle.getString("task3.title"));
        System.out.print(bundle.getString("task3.src"));
        String sourceFile = scanner.nextLine();

        System.out.print(bundle.getString("task3.enc"));
        String encryptedFile = scanner.nextLine();

        System.out.print(bundle.getString("task3.dec"));
        String decryptedFile = scanner.nextLine();

        System.out.print(bundle.getString("task3.key"));
        String keyInput = scanner.nextLine();
        char key = keyInput.isEmpty() ? 'K' : keyInput.charAt(0);

        logger.fine("Початок виконання Завдання 3.");
        try {
            fileService.encryptTextFile(sourceFile, encryptedFile, key);
            System.out.println(bundle.getString("task3.enc.success") + encryptedFile);
            logger.info("Файл успішно зашифровано.");

            fileService.decryptTextFile(encryptedFile, decryptedFile, key);
            System.out.println(bundle.getString("task3.dec.success") + decryptedFile);
            logger.info("Файл успішно розшифровано.");
        } catch (IOException e) {
            System.err.println(bundle.getString("error.file"));
            logger.log(Level.SEVERE, "Помилка шифрування", e);
        }

        System.out.println("\n" + bundle.getString("task4.title"));
        System.out.print(bundle.getString("task4.url"));
        String urlString = scanner.nextLine();
        logger.fine("Початок виконання Завдання 4. URL: " + urlString);

        Map<String, Integer> tagCounts = new HashMap<>();

        try {
            URL url = new URL(urlString);
            java.net.URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

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
            logger.info("HTML теги успішно проаналізовано.");

            System.out.println("\n" + bundle.getString("task4.sort.alpha"));
            Map<String, Integer> sortedByName = new TreeMap<>(tagCounts);
            for (Map.Entry<String, Integer> entry : sortedByName.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            System.out.println("\n" + bundle.getString("task4.sort.freq"));
            List<Map.Entry<String, Integer>> sortedByFreq = new ArrayList<>(tagCounts.entrySet());
            sortedByFreq.sort(Map.Entry.comparingByValue());
            for (Map.Entry<String, Integer> entry : sortedByFreq) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            System.out.print("\n" + bundle.getString("task4.save"));
            String dataFile = scanner.nextLine();
            if (dataFile.trim().isEmpty()) dataFile = "tags.dat";

            fileService.saveObject(tagCounts, dataFile);
            System.out.println(bundle.getString("task4.save.success"));
            logger.fine("Статистику збережено у файл: " + dataFile);

            System.out.print("\n" + bundle.getString("task4.search"));
            String searchTag = scanner.nextLine().toLowerCase();

            @SuppressWarnings("unchecked")
            Map<String, Integer> loadedData = (Map<String, Integer>) fileService.readObject(dataFile);

            if (loadedData.containsKey(searchTag)) {
                System.out.println(bundle.getString("task4.found") + loadedData.get(searchTag));
                logger.fine("Пошук тегу '" + searchTag + "' - успішно.");
            } else {
                System.out.println(bundle.getString("task4.notfound"));
                logger.fine("Пошук тегу '" + searchTag + "' - не знайдено.");
            }

        } catch (Exception e) {
            System.err.println(bundle.getString("task4.net.error") + e.getMessage());
            logger.log(Level.SEVERE, "Помилка Завдання 4", e);
        }

        logger.info("Програму успішно завершено.");
        scanner.close();
    }

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);

        try {
            FileHandler fileHandler = new FileHandler("application_log.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Не вдалося створити файл логування.");
        }
    }
}
