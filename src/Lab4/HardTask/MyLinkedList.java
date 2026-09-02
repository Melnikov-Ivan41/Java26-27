package Lab4.HardTask;

import java.util.Collection;

public class MyLinkedList<E> implements MyList<E> {

    // Оновлюємо внутрішній клас Node: тепер він теж дженерик <E>
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    public MyLinkedList() {
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
    }

    private Node<E> node(int index) {
        if (index < (size / 2)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public void add(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        if (index == size) {
            add(element);
        } else {
            Node<E> target = node(index);
            Node<E> pred = target.prev;

            Node<E> newNode = new Node<>(pred, element, target);
            target.prev = newNode;

            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> x = node(index);
        E element = x.item;

        Node<E> next = x.next;
        Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public void set(int index, E element) {
        checkIndex(index);
        Node<E> x = node(index);
        x.item = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
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
        for (Node<E> x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    // РЕАЛІЗАЦІЯ WILDCARD з Collection
    @Override
    public void addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) return;
        for (E o : c) {
            add(o);
        }
    }

    @Override
    public void addAll(int index, Collection<? extends E> c) {
        if (c == null || c.isEmpty()) return;
        int currIndex = index;
        for (E o : c) {
            add(currIndex++, o);
        }
    }
}