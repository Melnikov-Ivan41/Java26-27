package Lab2.HardTask;

public class MyCache {

    // Внутрішній клас Вузла
    private static class Node {
        final int hash;
        final Object key;
        Object value;        // Значення (може оновлюватися)
        long expiryTime;     // Час у мілісекундах, коли елемент "протухне"

        // Зв'язки для ХЕШ-ТАБЛИЦІ (пошук за O(1))
        Node nextInBucket;

        // Зв'язки для LRU-ЧЕРГИ (хронологія використання)
        Node prev; // Той, хто менш популярний (старіший)
        Node next; // Той, хто більш популярний (свіжіший)

        Node(int hash, Object key, Object value, long expiryTime) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    private Node[] table; // Наша "стіна з гачками"

    // Вказівники для LRU
    private Node head; // Найстаріший елемент (перший кандидат на виселення)
    private Node tail; // Найсвіжіший елемент (його чіпали останнім)

    private int size;
    private final int capacity; // Максимальна місткість кешу (наприклад, 100)

    // Конструктор кешу
    public MyCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість має бути більшою за 0");
        }
        this.capacity = capacity;
        this.table = new Node[capacity]; // Для простоти розмір масиву = місткості
    }

    private void moveToTail(Node node) {
        // Якщо вузол і так є хвостом, нічого не робимо
        if (node == tail) {
            return;
        }

        // КРОК 1: Вириваємо вузол з його поточного місця
        if (node == head) {
            // Якщо це була голова, то новою головою стає наступний за нею
            head = node.next;
            head.prev = null;
        } else {
            // Зв'язуємо сусідів цього вузла між собою (вони "беруться за руки" над ним)
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }

        // КРОК 2: Причіпляємо наш вузол у самий кінець (до старого хвоста)
        node.prev = tail;
        node.next = null;

        if (tail != null) {
            tail.next = node; // Старий хвіст тепер дивиться на наш вузол
        }
        tail = node; // Офіційно призначаємо наш вузол новим хвостом

        // Підстраховка: якщо кеш був пустим, то голова і хвіст - це один і той самий вузол
        if (head == null) {
            head = tail;
        }
    }

    public Object get(Object key) {
        // 1. Стандартний пошук у хеш-таблиці
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];

        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {

                // 2. Перевіряємо таймер смерті (TTL)
                if (System.currentTimeMillis() > current.expiryTime) {
                    remove(key); // Елемент протух. Видаляємо його по-справжньому
                    return null; // Робимо вигляд, що його тут ніколи й не було
                }

                // 3. Елемент живий! Оновлюємо його "свіжість" (LRU)
                moveToTail(current);

                // 4. Віддаємо значення користувачу
                return current.value;
            }
            current = current.nextInBucket;
        }

        return null; // Такого ключа немає
    }

    public void put(Object key, Object value, long ttl) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        // Висчитываем абсолютное время смерти (текущее время + сколько ему жить)
        long expiryTime = System.currentTimeMillis() + ttl;

        // --- ШАГ 1: Проверяем, вдруг этот ключ уже есть в Кеше ---
        Node current = table[index];
        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {

                // Ключ найден! Просто обновляем данные
                current.value = value;
                current.expiryTime = expiryTime;

                // И делаем его самым свежим (перекидываем в Хвост)
                moveToTail(current);
                return; // Работа закончена, выходим
            }
            current = current.nextInBucket;
        }

        // --- ШАГ 2: Проверяем лимиты ---
        if (size >= capacity) {
            removeEldest(); // Мест нет. Выгоняем самого старого!
        }

        // --- ШАГ 3: Создаем и добавляем новый элемент ---
        Node newNode = new Node(hash, key, value, expiryTime);

        // Вешаем на крючок массива (в начало локальной цепочки коллизий)
        newNode.nextInBucket = table[index];
        table[index] = newNode;

        // Добавляем в очередь LRU (делаем новым Хвостом)
        if (tail == null) {
            // Если кэш был абсолютно пустым
            head = newNode;
            tail = newNode;
        } else {
            // Привязываем к старому хвосту
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    private void removeEldest() {
        if (head == null) {
            return; // Кеш и так пуст
        }

        // Берем ключ у самого старого элемента
        Object eldestKey = head.key;

        // Удаляем его по-настоящему со всеми связями
        remove(eldestKey);
    }

    public boolean remove(Object key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = Math.abs(hash) % table.length;

        Node current = table[index];
        Node prevInBucket = null;

        // Шукаємо елемент у локальному ланцюжку колізій
        while (current != null) {
            if (current.hash == hash && (key == current.key || (key != null && key.equals(current.key)))) {

                // --- ФАЗА 1: Видаляємо з Хеш-таблиці (відриваємо від гачка) ---
                if (prevInBucket == null) {
                    // Якщо елемент висів першим
                    table[index] = current.nextInBucket;
                } else {
                    // Якщо елемент був усередині ланцюжка колізій
                    prevInBucket.nextInBucket = current.nextInBucket;
                }

                // --- ФАЗА 2: Видаляємо з LRU-черги (зшиваємо сусідів) ---
                if (current.prev == null) {
                    // Якщо ми видаляємо Голову (найстаріший елемент)
                    head = current.next;
                } else {
                    current.prev.next = current.next;
                }

                if (current.next == null) {
                    // Якщо ми видаляємо Хвіст (найсвіжіший елемент)
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                }

                // "Замітаємо сліди" (допомагаємо Garbage Collector'у)
                current.nextInBucket = null;
                current.prev = null;
                current.next = null;

                size--;
                return true; // Успішно видалили
            }

            // Крок уперед для пошуку
            prevInBucket = current;
            current = current.nextInBucket;
        }

        return false; // Ключа не було в кеші
    }

}
