package Lab4.HardTask;

public class MyLinkedHashSet<E> {

    // Оновлюємо внутрішній клас Node
    private static class Node<E> {
        final int hash;
        final E key; // Тепер ключ має тип E

        Node<E> nextInBucket;

        Node<E> before;
        Node<E> after;

        Node(int hash, E key) {
            this.hash = hash;
            this.key = key;
        }
    }

    // Масив тепер зберігає типізовані вузли
    private Node<E>[] table;

    private Node<E> head;
    private Node<E> tail;

    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        // Кастуємо сирий масив до масиву дженериків
        this.table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        Node<E> current = head;

        while (current != null) {
            int newIndex = Math.abs(current.hash) % newCapacity;

            current.nextInBucket = newTable[newIndex];
            newTable[newIndex] = current;

            current = current.after;
        }

        table = newTable;
    }

    // add приймає суворо тип E
    public boolean add(E key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node<E> current = table[index];
        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {
                return false;
            }
            current = current.nextInBucket;
        }

        Node<E> newNode = new Node<>(hash, key);
        newNode.nextInBucket = table[index];
        table[index] = newNode;

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.after = newNode;
            newNode.before = tail;
            tail = newNode;
        }

        size++;
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }
        return true;
    }

    // За стандартом Java, remove приймає Object
    public boolean remove(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node<E> current = table[index];
        Node<E> prevInBucket = null;

        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {
                if (prevInBucket == null) {
                    table[index] = current.nextInBucket;
                } else {
                    prevInBucket.nextInBucket = current.nextInBucket;
                }

                if (current.before == null) {
                    head = current.after;
                } else {
                    current.before.after = current.after;
                }
                if (current.after == null) {
                    tail = current.before;
                } else {
                    current.after.before = current.before;
                }
                current.nextInBucket = null;
                current.before = null;
                current.after = null;

                size--;
                return true;
            }

            prevInBucket = current;
            current = current.nextInBucket;
        }

        return false;
    }

    // За стандартом Java, contains приймає Object
    public boolean contains(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node<E> current = table[index];

        while (current != null) {
            if (current.hash == hash &&
                    (key == current.key || (key != null && key.equals(current.key)))) {
                return true;
            }
            current = current.nextInBucket;
        }

        return false;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = head; x != null; x = x.after) {
            result[i++] = x.key;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }

        head = null;
        tail = null;

        size = 0;
    }
}