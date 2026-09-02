package Lab4.HardTask;

public class MyCache<K, V> {

    // Оновлюємо вузол для підтримки Key та Value
    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        long expiryTime;

        Node<K, V> nextInBucket;

        Node<K, V> prev;
        Node<K, V> next;

        Node(int hash, K key, V value, long expiryTime) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    private Node<K, V>[] table;
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public MyCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість має бути більшою за 0");
        }
        this.capacity = capacity;
        // Кастуємо сирий масив до типізованого
        this.table = (Node<K, V>[]) new Node[capacity];
    }

    private void moveToTail(Node<K, V> node) {
        if (node == tail) {
            return;
        }

        if (node == head) {
            head = node.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }

        node.prev = tail;
        node.next = null;

        if (tail != null) {
            tail.next = node;
        }
        tail = node;

        if (head == null) {
            head = tail;
        }
    }

    // Повертає типізоване значення V
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("Ключ не може бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node<K, V> current = table[index];

        while (current != null) {
            if (current.hash == hash && (key == current.key || key.equals(current.key))) {

                if (System.currentTimeMillis() > current.expiryTime) {
                    remove(key);
                    return null;
                }

                moveToTail(current);

                return current.value;
            }
            current = current.nextInBucket;
        }

        return null;
    }

    // Приймає суворо типізовані K та V
    public void put(K key, V value, long ttl) {
        if (key == null || value == null) {
            throw new NullPointerException("Ключ і значення не можуть бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        long expiryTime = System.currentTimeMillis() + ttl;

        Node<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && (key == current.key || key.equals(current.key))) {
                current.value = value;
                current.expiryTime = expiryTime;

                moveToTail(current);
                return;
            }
            current = current.nextInBucket;
        }

        if (size >= capacity) {
            removeEldest();
        }
        Node<K, V> newNode = new Node<>(hash, key, value, expiryTime);
        newNode.nextInBucket = table[index];
        table[index] = newNode;

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    private void removeEldest() {
        if (head == null) {
            return;
        }
        K eldestKey = head.key; // Типізований ключ
        remove(eldestKey);
    }

    public boolean remove(Object key) {
        if (key == null) {
            throw new NullPointerException("Ключ не може бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node<K, V> current = table[index];
        Node<K, V> prevInBucket = null;

        while (current != null) {
            if (current.hash == hash && (key == current.key || key.equals(current.key))) {
                if (prevInBucket == null) {
                    table[index] = current.nextInBucket;
                } else {
                    prevInBucket.nextInBucket = current.nextInBucket;
                }

                if (current.prev == null) {
                    head = current.next;
                } else {
                    current.prev.next = current.next;
                }

                if (current.next == null) {
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                }

                current.nextInBucket = null;
                current.prev = null;
                current.next = null;

                size--;
                return true;
            }

            prevInBucket = current;
            current = current.nextInBucket;
        }

        return false;
    }
}