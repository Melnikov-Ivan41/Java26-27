package Lab4.HardTask;

import java.util.Collection;
import java.util.RandomAccess;

public class MyArrayList<E> implements MyList<E>, RandomAccess {

    private Object[] elements;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
    }

    @Override
    public void add(E e) {
        ensureCapacity(size + 1);
        elements[size++] = e;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) return;
        Object[] arr = c.toArray();
        ensureCapacity(size + arr.length);
        System.arraycopy(arr, 0, elements, size, arr.length);
        size += arr.length;
    }

    @Override
    public void addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index + ", Розмір: " + size);
        }
        if (c == null || c.isEmpty()) return;

        Object[] arr = c.toArray();
        ensureCapacity(size + arr.length);
        System.arraycopy(elements, index, elements, index + arr.length, size - index);
        System.arraycopy(arr, 0, elements, index, arr.length);
        size += arr.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        checkIndex(index);
        E removedElement = (E) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public void set(int index, E element) {
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
        return -1;
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
