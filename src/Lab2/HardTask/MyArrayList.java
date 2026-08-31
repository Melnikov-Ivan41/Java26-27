package Lab2.HardTask;

import java.util.RandomAccess;

// Клас імплементує наш інтерфейс MyList та маркерний інтерфейс RandomAccess
public class MyArrayList implements MyList, RandomAccess {

    private Object[] elements; // Внутрішній масив для зберігання даних
    private int size;          // Фактична кількість доданих елементів

    private static final int DEFAULT_CAPACITY = 10; // Початковий розмір масиву

    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Допоміжний метод: якщо масив заповнений, збільшуємо його вдвічі
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            // Швидке системне копіювання старого масиву в новий
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    // Перевірка, чи не виходить індекс за межі списку
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
    }

    @Override
    public void add(Object e) {
        ensureCapacity(size + 1);
        elements[size++] = e; // Додаємо в кінець і збільшуємо size
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        ensureCapacity(size + 1);
        // Зсуваємо елементи вправо, щоб звільнити місце
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        if (c == null || c.length == 0) return;
        ensureCapacity(size + c.length);
        System.arraycopy(c, 0, elements, size, c.length);
        size += c.length;
    }

    @Override
    public void addAll(int index, Object[] c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        if (c == null || c.length == 0) return;

        ensureCapacity(size + c.length);
        // Спочатку зсуваємо існуючі елементи вправо
        System.arraycopy(elements, index, elements, index + c.length, size - index);
        // Вставляємо новий масив у звільнене місце
        System.arraycopy(c, 0, elements, index, c.length);
        size += c.length;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return elements[index];
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object removedElement = elements[index];
        // Зсуваємо елементи вліво, затираючи видалений
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // Очищуємо останній елемент для Garbage Collector
        return removedElement;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        elements[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) return i;
            }
        }
        return -1; // Якщо елемент не знайдено
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }
}
