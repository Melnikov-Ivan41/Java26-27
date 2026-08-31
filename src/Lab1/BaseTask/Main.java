package Lab1.BaseTask;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueChoice;
        System.out.println("Програма для пошуку простого числа з максимальною кількістю нулів у двійковому коді.");

        // Головний цикл програми, який дозволяє повторювати виконання
        do {
            int n = 0;

            // Безпечне зчитування та валідація вводу користувача
            while (true) {
                System.out.print("\nВведіть ціле число n (n >= 2): ");
                try {
                    n = scanner.nextInt();
                    if (n >= 2 && n <= 10000) {
                        break; // Ввід коректний, виходимо з циклу зчитування
                    } else {
                        System.out.println("Помилка: число має бути більше або дорівнювати 2 та менше або дорівнювати 10000.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Помилка: введено некоректні дані. Будь ласка, введіть ціле число.");
                    scanner.next(); // Очищення буфера сканера від некоректного вводу
                }
            }

            int bestPrime = -1;
            int maxZeros = -1;

            System.out.println("\n--- Процес аналізу простих чисел ---");

            // Перебір всіх чисел від 2 до заданого n
            for (int i = 2; i <= n; i++) {
                if (isPrime(i)) {
                    // Якщо число просте, рахуємо кількість нулів у його двійковій формі
                    int zerosCount = countZerosInBinary(i);

                    // Виводимо інформацію про поточне просте число у вигляді рівних колонок
                    System.out.printf("Число: %-5d | Двійкова форма: %-12s | Кількість нулів: %d%n",
                            i, Integer.toBinaryString(i), zerosCount);

                    // Оновлюємо рекорд, якщо знайшли більше нулів
                    if (zerosCount > maxZeros) {
                        maxZeros = zerosCount;
                        bestPrime = i;
                    }
                }
            }

            // Виведення фінального результату
            if (bestPrime != -1) {
                System.out.println("\n--- Фінальний результат ---");
                System.out.println("Просте число з максимальною кількістю нулів: " + bestPrime);
                System.out.println("Його двійкове представлення: " + Integer.toBinaryString(bestPrime));
                System.out.println("Кількість нулів: " + maxZeros);
            } else {
                System.out.println("\nУ заданому діапазоні не знайдено простих чисел.");
            }

            // Запит на продовження роботи
            System.out.print("\nБажаєте перевірити інше число? (т/н): ");
            continueChoice = scanner.next();

            // Програма продовжить роботу, якщо користувач введе "т", "Т", "y", "Y" або "так"
        } while (continueChoice.equalsIgnoreCase("т") ||
                continueChoice.equalsIgnoreCase("y") ||
                continueChoice.equalsIgnoreCase("так"));

        System.out.println("Завершення роботи програми.");
        scanner.close(); // Закриваємо сканер для звільнення ресурсів
    }

    /**
     * Метод для перевірки, чи є число простим.
     */
    private static boolean isPrime(int number) {
        if (number < 2) return false;

        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод для підрахунку кількості нулів у двійковому поданні числа.
     */
    private static int countZerosInBinary(int number) {
        int zeros = 0;
        int temp = number;

        while (temp > 0) {
            if (temp % 2 == 0) {
                zeros++;
            }
            temp /= 2;
        }
        return zeros;
    }
}