package Lab3.BaseTask;

import java.util.List;
import java.util.Scanner;

public class BookController {
    private BookModel model;
    private BookView view;
    private Scanner scanner;

    public BookController(BookModel model, BookView view) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    // Головний цикл обробки команд
    public void run() {
        while (true) {
            view.printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAuthorSearch();
                    break;
                case "2":
                    handlePublisherSearch();
                    break;
                case "3":
                    handleYearSearch();
                    break;
                case "4":
                    handleSortByPublisher();
                    break;
                case "5":
                    view.printBooks(model.getAllBooks(), "Повний каталог книг");
                    break;
                case "0":
                    view.printMessage("Завершення роботи програми. До побачення!");
                    return;
                default:
                    view.printMessage("Невірна команда. Спробуйте ще раз.");
            }
        }
    }

    private void handleAuthorSearch() {
        System.out.print("Введіть автора (або його частину): ");
        String author = scanner.nextLine().trim();
        List<Book> result = model.getBooksByAuthor(author);
        view.printBooks(result, "Результати пошуку для автора: " + author);
    }

    private void handlePublisherSearch() {
        System.out.print("Введіть назву видавництва: ");
        String publisher = scanner.nextLine().trim();
        List<Book> result = model.getBooksByPublisher(publisher);
        view.printBooks(result, "Результати пошуку для видавництва: " + publisher);
    }

    private void handleYearSearch() {
        System.out.print("Введіть рік: ");
        try {
            int year = Integer.parseInt(scanner.nextLine().trim());
            List<Book> result = model.getBooksPublishedAfter(year);
            view.printBooks(result, "Книги, видані пізніше " + year + " року");
        } catch (NumberFormatException e) {
            view.printMessage("Помилка: рік має бути цілим числом.");
        }
    }

    private void handleSortByPublisher() {
        model.sortBooksByPublisher();
        view.printMessage("Масив успішно відсортовано за видавництвами!");
        view.printBooks(model.getAllBooks(), "Каталог книг після сортування");
    }
}