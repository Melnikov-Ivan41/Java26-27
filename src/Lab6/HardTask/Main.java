package Lab6.HardTask;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("=== Червоно-чорне дерево (Hard Task) ===");
        System.out.print("Введіть кількість елементів для початкової побудови дерева: ");

        int n;
        try {
            n = Integer.parseInt(scanner.nextLine());
            if (n <= 0) {
                System.out.println("Кількість елементів має бути більшою за 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введено некоректне число.");
            return;
        }

        int[] array = new int[n];

        System.out.println("Оберіть спосіб заповнення:");
        System.out.println("1 - Ввести числа вручну з клавіатури");
        System.out.println("2 - Згенерувати випадково");
        System.out.print("Ваш вибір: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("Введіть " + n + " чисел (через пробіл або Enter):");
            for (int i = 0; i < n; i++) {
                array[i] = scanner.nextInt();
            }
            scanner.nextLine(); // Очищуємо буфер від символу нового рядка після nextInt()
        } else {
            System.out.println("Генеруємо числа в діапазоні від -100 до 100...");
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(201) - 100;
            }
        }

        // 1. Дерево з елементами у початковому (випадковому або введеному) порядку
        System.out.println("\n--- 1. Додавання елементів у початковому порядку ---");
        System.out.println("Початковий масив: " + Arrays.toString(array));

        RedBlackTree tree = new RedBlackTree();
        for (int num : array) {
            tree.insert(num);
        }

        System.out.println("\nВідображення дерева (вставка в початковому порядку):");
        tree.printTree();

        System.out.print("Відсортований обхід (In-Order): ");
        tree.inOrder();

        // 2. Дерево з елементами у впорядкованому вигляді
        System.out.println("\n--- 2. Додавання елементів у впорядкованому вигляді ---");
        Arrays.sort(array);
        System.out.println("Відсортований масив: " + Arrays.toString(array));

        RedBlackTree sortedTree = new RedBlackTree();
        for (int num : array) {
            sortedTree.insert(num);
        }

        System.out.println("\nВідображення дерева (вставка по зростанню):");
        sortedTree.printTree();

        System.out.print("Відсортований обхід (In-Order): ");
        sortedTree.inOrder();

        // --- 3. Інтерактивний режим вставки ---
        System.out.println("\n=============================================");
        System.out.println("=== Інтерактивний режим тестування балансу ===");
        System.out.println("=============================================");

        while (true) {
            System.out.print("\nВведіть нове число для додавання в ПЕРШЕ дерево (або 'q' для виходу): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Завершення роботи. Дякую!");
                break;
            }

            try {
                int newValue = Integer.parseInt(input);
                tree.insert(newValue);
                System.out.println("\nЕлемент " + newValue + " успішно додано! Оновлене дерево:");
                tree.printTree();
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть коректне ціле число або 'q' для виходу.");
            }
        }

        scanner.close();
    }
}