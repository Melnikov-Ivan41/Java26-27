package Lab2.HardTask;

public class MyCache {

    private static class Node {
        final int hash;
        final Object key;
        Object value;
        long expiryTime;

        Node nextInBucket;

        Node prev;
        Node next;

        Node(int hash, Object key, Object value, long expiryTime) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    private Node[] table;
    private Node head;
    private Node tail;
    private int size;
    private final int capacity;

    public MyCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість має бути більшою за 0");
        }
        this.capacity = capacity;
        this.table = new Node[capacity];
    }

    private void moveToTail(Node node) {
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

    public Object get(Object key) {
        if (key == null) {
            throw new NullPointerException("Ключ не може бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];

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

    public void put(Object key, Object value, long ttl) {
        if (key == null || value == null) {
            throw new NullPointerException("Ключ і значення не можуть бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        long expiryTime = System.currentTimeMillis() + ttl;

        Node current = table[index];
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
        Node newNode = new Node(hash, key, value, expiryTime);
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
        Object eldestKey = head.key;
        remove(eldestKey);
    }

    public boolean remove(Object key) {
        if (key == null) {
            throw new NullPointerException("Ключ не може бути null");
        }
        int hash = key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];
        Node prevInBucket = null;

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
