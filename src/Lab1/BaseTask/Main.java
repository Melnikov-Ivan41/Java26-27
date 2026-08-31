package Lab1.BaseTask;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueChoice;
        System.out.println("Програма для пошуку простого числа з максимальною кількістю нулів у двійковому коді.");

        do {
            int n = 0;

            while (true) {
                System.out.print("\nВведіть ціле число n (n >= 2): ");
                try {
                    n = scanner.nextInt();
                    if (n >= 2 && n <= 10000) {
                        break;
                    } else {
                        System.out.println("Помилка: число має бути більше або дорівнювати 2 та менше або дорівнювати 10000.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Помилка: введено некоректні дані. Будь ласка, введіть ціле число.");
                    scanner.next();
                }
            }

            int bestPrime = -1;
            int maxZeros = -1;

            System.out.println("\n--- Процес аналізу простих чисел ---");

            for (int i = 2; i <= n; i++) {
                if (isPrime(i)) {
                    int zerosCount = countZerosInBinary(i);

                    System.out.printf("Число: %-5d | Двійкова форма: %-12s | Кількість нулів: %d%n",
                            i, Integer.toBinaryString(i), zerosCount);

                    if (zerosCount > maxZeros) {
                        maxZeros = zerosCount;
                        bestPrime = i;
                    }
                }
            }

            if (bestPrime != -1) {
                System.out.println("\n--- Фінальний результат ---");
                System.out.println("Просте число з максимальною кількістю нулів: " + bestPrime);
                System.out.println("Його двійкове представлення: " + Integer.toBinaryString(bestPrime));
                System.out.println("Кількість нулів: " + maxZeros);
            } else {
                System.out.println("\nУ заданому діапазоні не знайдено простих чисел.");
            }

            System.out.print("\nБажаєте перевірити інше число? (т/н): ");
            continueChoice = scanner.next();

        } while (continueChoice.equalsIgnoreCase("т") ||
                continueChoice.equalsIgnoreCase("y") ||
                continueChoice.equalsIgnoreCase("так"));

        System.out.println("Завершення роботи програми.");
        scanner.close();
    }

    private static boolean isPrime(int number) {
        if (number < 2) return false;

        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

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