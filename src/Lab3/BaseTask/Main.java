package Lab3.BaseTask;

public class Main {
    public static void main(String[] args) {
        BookModel model = new BookModel();
        BookView view = new BookView();
        BookController controller = new BookController(model, view);

        view.printBooks(model.getAllBooks(), "Початковий стан масиву книг (до обробки)");

        controller.run();
    }
}