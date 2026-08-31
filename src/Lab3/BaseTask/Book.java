package Lab3.BaseTask;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int year;
    private int pages;
    private double price;

    // Конструктор для швидкого створення книги
    public Book(String title, String author, String publisher, int year, int pages, double price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.price = price;
    }

    // Геттери, які знадобляться нам для пошуку та сортування
    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public double getPrice() {
        return price;
    }

    // Перевизначаємо toString() для красивого виведення на екран
    @Override
    public String toString() {
        return String.format("Книга: «%s» | Автор: %s | Видавництво: %s | Рік: %d | Стор: %d | Ціна: %.2f грн",
                title, author, publisher, year, pages, price);
    }
}
