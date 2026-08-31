package Lab2.HardTask;

public class MyLinkedList implements MyList {

    // 1. Внутрішній клас, який описує один "вагончик" (Вузол)
    private static class Node {
        Object item; // Самі дані, які ми зберігаємо
        Node next;   // Вказівник на наступний вагончик
        Node prev;   // Вказівник на попередній вагончик

        Node(Node prev, Object element, Node next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size = 0; // Кількість елементів
    private Node first;   // Вказівник на самий перший вагон (голова)
    private Node last;    // Вказівник на самий останній вагон (хвіст)

    public MyLinkedList() {
        // Конструктор порожній, бо при створенні немає ніяких вагончиків
    }

    // Допоміжний метод: перевірка на адекватність індексу
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
    }

    // Допоміжний метод: пошук КОНКРЕТНОГО ВАГОНЧИКА за індексом
    private Node node(int index) {
        // Розумний пошук: якщо індекс у першій половині, йдемо з початку
        if (index < (size / 2)) {
            Node x = first;
            for (int i = 0; i < index; i++) {
                x = x.next; // Переходимо до наступного
            }
            return x;
        } else {
            // Якщо у другій половині - йдемо з кінця (хвоста) назад
            Node x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev; // Переходимо до попереднього
            }
            return x;
        }
    }

    @Override
    public void add(Object e) {
        // Додавання в кінець списку
        final Node l = last; // Запам'ятовуємо поточний останній вагон
        // Створюємо новий вагон: попередній - це старий останній, наступного немає (null)
        final Node newNode = new Node(l, e, null);
        last = newNode; // Тепер наш новий вагон стає офіційним останнім

        if (l == null) {
            // Якщо список був порожнім, то новий вагон одночасно і перший
            first = newNode;
        } else {
            // Якщо вагони були, кажемо старому останньому, що за ним тепер стоїть новий
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, Object element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        if (index == size) {
            add(element); // Якщо індекс дорівнює розміру, просто кидаємо в кінець
        } else {
            // Вставка перед існуючим вузлом
            Node target = node(index); // Знаходимо вагон, який зараз на цьому місці
            Node pred = target.prev;   // Знаходимо того, хто стоїть перед ним

            // Створюємо новий вагон, вклинюючи його між pred і target
            Node newNode = new Node(pred, element, target);
            target.prev = newNode;

            if (pred == null) {
                first = newNode; // Якщо вставляємо в самий початок
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return node(index).item; // Знаходимо вагон і дістаємо з нього вантаж (item)
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Node x = node(index); // Знаходимо вагон, який треба знищити
        Object element = x.item; // Зберігаємо вантаж, щоб повернути його

        Node next = x.next;
        Node prev = x.prev;

        // "Відчіпляємо" вагон від попереднього
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        // "Відчіпляємо" вагон від наступного
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null; // Очищуємо дані для збирача сміття
        size--;
        return element;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        Node x = node(index);
        x.item = element; // Просто замінюємо вантаж у знайденому вагоні
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node x = first; x != null; x = x.next) {
                if (x.item == null) return index;
                index++;
            }
        } else {
            for (Node x = first; x != null; x = x.next) {
                if (o.equals(x.item)) return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        // Проходимо по всіх вагонах від першого до останнього
        for (Node x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    @Override
    public void addAll(Object[] c) {
        if (c == null || c.length == 0) return;
        for (Object o : c) {
            add(o);
        }
    }

    @Override
    public void addAll(int index, Object[] c) {
        if (c == null || c.length == 0) return;
        // Для спрощення: масив додаємо по одному елементу, зсуваючи індекс
        int currIndex = index;
        for (Object o : c) {
            add(currIndex++, o);
        }
    }
}
