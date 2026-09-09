package Lab7.SimpleTask;

import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Lab 7. Лямбда-вирази та Stream API (Варіант 7) ===");
        System.out.print("Введіть межу n (від 2 до 10000): ");

        if (!scanner.hasNextInt()) {
            System.out.println("Помилка: потрібно ввести ціле число.");
            return;
        }
        int n = scanner.nextInt();

        if (n < 2 || n > 10000) {
            System.out.println("Помилка: число n має бути в діапазоні від 2 до 10000.");
            return;
        }

        System.out.println("\n--- Аналіз простих чисел ---");

        Integer result = IntStream.rangeClosed(2, n)
                .filter(Main::isPrime)
                .boxed()
                .peek(p -> System.out.printf("Просте число: %-5d | Двійковий код: %-14s | Нулів: %d%n",
                        p, Integer.toBinaryString(p), countZerosInBinary(p)))
                .max(Comparator.comparingLong(Main::countZerosInBinary))
                .orElse(null);

        if (result != null) {
            System.out.println("\n==========================================");
            System.out.println("РЕЗУЛЬТАТ (Число з максимальною кількістю нулів):");
            System.out.println("Шукане число: " + result);
            System.out.println("Двійкова форма: " + Integer.toBinaryString(result));
            System.out.println("Кількість нулів: " + countZerosInBinary(result));
            System.out.println("==========================================");
        }

        scanner.close();
    }

    private static boolean isPrime(int number) {
        if (number < 2) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(i -> number % i == 0);
    }

    private static long countZerosInBinary(int number) {
        return Integer.toBinaryString(number)
                .chars()
                .filter(ch -> ch == '0')
                .count();
    }
}