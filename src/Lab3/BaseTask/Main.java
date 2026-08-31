package Lab3.BaseTask;

public class Main {
    public static void main(String[] args) {
        // 1. Ініціалізація компонентів MVC
        BookModel model = new BookModel();
        BookView view = new BookView();
        BookController controller = new BookController(model, view);

        // 2. Обов'язкова вимога завдання: вивести вихідний масив після створення
        view.printBooks(model.getAllBooks(), "Початковий стан масиву книг (до обробки)");

        // 3. Запуск контролера
        controller.run();
    }
}