package Lab2.HardTask;

public class MyLinkedHashSet {

    // Внутрішній клас, який зберігає дані
    private static class Node {
        final int hash;     // Хэш-код об'єкта (для швидкості)
        final Object key;   // Самі дані (наприклад, твій Person)

        // 1. Зв'язки для ХЭШ-ТАБЛИЦІ (якщо два об'єкти потраплять в одну комірку)
        Node nextInBucket;

        // 2. Зв'язки для ДВУЗВ'ЯЗНОГО СПИСКУ (щоб пам'ятати порядок додавання)
        Node before;
        Node after;

        Node(int hash, Object key) {
            this.hash = hash;
            this.key = key;
        }
    }

    // Головний масив "відер", де фізично лежать дані
    private Node[] table;

    // Вказівники на початок і кінець для збереження порядку (як у MyLinkedList)
    private Node head;
    private Node tail;

    private int size;
    private static final int DEFAULT_CAPACITY = 16; // Розмір таблиці за замовчуванням
    private static final float LOAD_FACTOR = 0.75f;

    public MyLinkedHashSet() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    private void resize() {
        // 1. Створюємо новий масив, вдвічі більший за старий
        int newCapacity = table.length * 2;
        Node[] newTable = new Node[newCapacity];

        // 2. Ідемо по нашій історичній гірлянді від першого до останнього!
        Node current = head;

        while (current != null) {
            // 3. Перераховуємо індекс для нового масиву
            int newIndex = Math.abs(current.hash) % newCapacity;

            // 4. Підвішуємо вузол у нове відро
            current.nextInBucket = newTable[newIndex];
            newTable[newIndex] = current;

            // 5. Йдемо до наступного доданого елемента
            current = current.after;
        }

        // 6. Замінюємо старий масив на новий
        table = newTable;
    }

    public boolean add(Object key) {
        // 1. Обчислюємо хеш та індекс комірки (відра) в масиві
        int hash = (key == null) ? 0 : key.hashCode();
        // Використовуємо модуль, щоб індекс точно потрапив у межі нашого масиву
        int index = Math.abs(hash) % table.length;

        // --- ФАЗА 1: РОБОТА З ХЕШ-ТАБЛИЦЕЮ ---

        // 2. Шукаємо дублікати в поточній комірці
        Node current = table[index];
        while (current != null) {
            // Якщо хеші збігаються І (це той самий об'єкт АБО об'єкти рівні за equals)
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {
                return false; // Такий елемент вже є! Відхиляємо додавання
            }
            current = current.nextInBucket; // Йдемо далі по ланцюжку колізій
        }

        // 3. Дублікатів немає, створюємо новий вузол
        Node newNode = new Node(hash, key);

        // 4. Підвішуємо новий вузол у комірку масиву (вставляємо на самий початок ланцюжка)
        newNode.nextInBucket = table[index];
        table[index] = newNode;


        // --- ФАЗА 2: РОБОТА З ДВУЗВ'ЯЗНИМ СПИСКОМ ---

        // 5. Прив'язуємо новий вузол до загальної гірлянди (в самий кінець)
        if (tail == null) {
            // Якщо це взагалі перший елемент у всій структурі
            head = newNode;
            tail = newNode;
        } else {
            // Кажемо старому хвосту, що за ним тепер стоїть наш новий вузол
            tail.after = newNode;
            // Кажемо новому вузлу, що перед ним - старий хвіст
            newNode.before = tail;
            // Офіційно призначаємо новий вузол хвостом
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
        // Ця змінна потрібна, щоб пам'ятати, хто стояв ПЕРЕД нами в кошику
        Node prevInBucket = null;

        // Йдемо по ланцюжку колізій у конкретному кошику
        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {

                // --- ФАЗА 1: Витягуємо вузол із ХЕШ-ТАБЛИЦІ (кошика) ---
                if (prevInBucket == null) {
                    // Якщо елемент висів найпершим прямо на гачку
                    table[index] = current.nextInBucket;
                } else {
                    // Якщо він був усередині ланцюжка:
                    // Просимо попереднього перестрибнути через наш вузол
                    prevInBucket.nextInBucket = current.nextInBucket;
                }

                // --- ФАЗА 2: Витягуємо вузол із ІСТОРИЧНОЇ ГІРЛЯНДИ ---
                // Відв'язуємо від того, хто був доданий ПЕРЕД нами
                if (current.before == null) {
                    head = current.after; // Якщо ми були найпершим доданим елементом
                } else {
                    current.before.after = current.after;
                }

                // Відв'язуємо від того, хто був доданий ПІСЛЯ нас
                if (current.after == null) {
                    tail = current.before; // Якщо ми були останнім (хвостом)
                } else {
                    current.after.before = current.before;
                }

                // "Замітаємо сліди" - обнуляємо посилання самого вузла для GC
                current.nextInBucket = null;
                current.before = null;
                current.after = null;

                size--;
                return true; // Успішно видалили!
            }

            // Крок уперед: поточний стає попереднім, а ми йдемо до наступного
            prevInBucket = current;
            current = current.nextInBucket;
        }

        return false; // Такого елемента не було в колекції
    }

    public boolean contains(Object key) {
        // 1. Обчислюємо хеш та індекс кошика
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        // 2. Одразу стрибаємо в потрібний кошик
        Node current = table[index];

        // 3. Перевіряємо локальний ланцюжок колізій (якщо він є)
        while (current != null) {
            // Якщо хеші збігаються І самі об'єкти рівні
            if (current.hash == hash &&
                    (key == current.key || (key != null && key.equals(current.key)))) {
                return true; // Елемент знайдено!
            }
            current = current.nextInBucket; // Йдемо до наступного в кошику
        }

        return false; // Такого елемента немає
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
        // 1. Очищаємо масив (відв'язуємо всі кошики)
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }

        // 2. Відв'язуємо історичну гірлянду
        head = null;
        tail = null;

        // 3. Обнуляємо лічильник
        size = 0;
    }
}
