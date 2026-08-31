package Lab2.HardTask;

public class MyLinkedHashSet {

    private static class Node {
        final int hash;
        final Object key;

        Node nextInBucket;

        Node before;
        Node after;

        Node(int hash, Object key) {
            this.hash = hash;
            this.key = key;
        }
    }

    private Node[] table;

    private Node head;
    private Node tail;

    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    public MyLinkedHashSet() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    private void resize() {

        int newCapacity = table.length * 2;
        Node[] newTable = new Node[newCapacity];


        Node current = head;

        while (current != null) {

            int newIndex = Math.abs(current.hash) % newCapacity;


            current.nextInBucket = newTable[newIndex];
            newTable[newIndex] = current;

            current = current.after;
        }


        table = newTable;
    }

    public boolean add(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;
        Node current = table[index];
        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {
                return false;
            }
            current = current.nextInBucket;
        }

        Node newNode = new Node(hash, key);
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

    public boolean remove(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];
        Node prevInBucket = null;

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

    public boolean contains(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];

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
        for (Node x = head; x != null; x = x.after) {
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
