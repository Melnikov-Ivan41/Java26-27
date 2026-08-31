package Lab3.BaseTask;

import java.util.List;

public class BookView {

    // Виведення головного меню
    public void printMenu() {
        System.out.println("\n========== МЕНЮ КАТАЛОГУ КНИГ ==========");
        System.out.println("1. Знайти книги за автором");
        System.out.println("2. Знайти книги за видавництвом");
        System.out.println("3. Знайти книги, видані після вказаного року");
        System.out.println("4. Відсортувати книги за видавництвами");
        System.out.println("5. Показати всі книги");
        System.out.println("0. Вихід");
        System.out.print("Оберіть пункт: ");
    }

    // Універсальний метод для виведення списку знайдених книг
    public void printBooks(List<Book> books, String title) {
        System.out.println("\n--- " + title + " ---");
        // Обробка вимоги: якщо даних не знайдено, вивести повідомлення
        if (books == null || books.isEmpty()) {
            System.out.println("За вашим запитом нічого не знайдено.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    // Перевантажений метод для виведення масиву (знадобиться для вихідного масиву)
    public void printBooks(Book[] books, String title) {
        System.out.println("\n--- " + title + " ---");
        if (books == null || books.length == 0) {
            System.out.println("Каталог порожній.");
            return;
        }

        for (int i = 0; i < books.length; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
    }

    // Виведення службових повідомлень
    public void printMessage(String message) {
        System.out.println(message);
    }
}
