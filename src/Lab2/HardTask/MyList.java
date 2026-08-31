package Lab2.HardTask;

public interface MyList {
    void add(Object e); // додає елемент в кінець списку
    void add(int index, Object element); // додає елемент у вказане місце списку
    void addAll(Object[] c); // додає масив елементів в кінець списку
    void addAll(int index, Object[] c); // додає масив елементів у вказане місце списку
    Object get(int index); // повертає елемент за індексом
    Object remove(int index); // видаляє елемент за індексом
    void set(int index, Object element); // змінює значення елемента
    int indexOf(Object o); // пошук індексу за значенням елемента
    int size(); // розмір списку
    Object[] toArray(); // перетворює список в масив об'єктів
}
