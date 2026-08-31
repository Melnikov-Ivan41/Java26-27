package Lab3.BaseTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BookModel {
    private Book[] books;

    public BookModel() {
        books = new Book[] {
                new Book("Кобзар", "Тарас Шевченко", "Основа", 2020, 720, 450.0),
                new Book("Тіні забутих предків", "Михайло Коцюбинський", "Фоліо", 2018, 320, 250.0),
                new Book("Тигролови", "Іван Багряний", "А-БА-БА-ГА-ЛА-МА-ГА", 2021, 400, 300.0),
                new Book("Захар Беркут", "Іван Франко", "Фоліо", 2019, 280, 200.0),
                new Book("Місто", "Валер'ян Підмогильний", "Основа", 2022, 350, 320.0),
                new Book("Кайдашева сім'я", "Іван Нечуй-Левицький", "Ранок", 2017, 300, 180.0),
                new Book("Чорна рада", "Пантелеймон Куліш", "А-БА-БА-ГА-ЛА-МА-ГА", 2020, 410, 350.0),
                new Book("Хіба ревуть воли...", "Панас Мирний", "Фоліо", 2016, 500, 400.0),
                new Book("Маруся Чурай", "Ліна Костенко", "А-БА-БА-ГА-ЛА-МА-ГА", 2023, 200, 280.0),
                new Book("Лісова пісня", "Леся Українка", "Основа", 2021, 150, 220.0)
        };
    }

    public Book[] getAllBooks() {
        return books;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getBooksByPublisher(String publisher) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getPublisher().toLowerCase().contains(publisher.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getBooksPublishedAfter(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() > year) {
                result.add(book);
            }
        }
        return result;
    }

    public void sortBooksByPublisher() {
        Arrays.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getPublisher().compareToIgnoreCase(b2.getPublisher());
            }
        });
    }
}