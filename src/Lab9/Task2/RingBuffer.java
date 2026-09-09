package Lab9.Task2;

public class RingBuffer {
    private final String[] buffer;
    private int head = 0;
    private int tail = 0;
    private final int capacity;

    public RingBuffer(int capacity) {
        this.capacity = capacity + 1;
        this.buffer = new String[this.capacity];
    }

    public synchronized void put(String message) throws InterruptedException {
        while ((tail + 1) % capacity == head) {
            wait();
        }

        buffer[tail] = message;
        tail = (tail + 1) % capacity;

        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (head == tail) {
            wait();
        }

        String message = buffer[head];
        head = (head + 1) % capacity;

        notifyAll();
        return message;
    }
}
