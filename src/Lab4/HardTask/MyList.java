package Lab4.HardTask;
import java.util.Collection;

public interface MyList<E> {
    void add(E e);
    void add(int index, E element);

    // Використання wildcard для колекцій
    void addAll(Collection<? extends E> c);
    void addAll(int index, Collection<? extends E> c);

    E get(int index);
    E remove(int index);
    void set(int index, E element);

    // За стандартом Java indexOf завжди приймає Object
    int indexOf(Object o);
    int size();
    Object[] toArray();
}