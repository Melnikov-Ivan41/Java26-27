package Lab9.Task2;

public class Main {
    public static void main(String[] args) {
        RingBuffer buffer1 = new RingBuffer(10);
        RingBuffer buffer2 = new RingBuffer(10);

        System.out.println("=== Task 2: Producer-Consumer (Ring Buffer) ===");

        for (int i = 1; i <= 5; i++) {
            Thread generator = new Thread(() -> {
                int messageCount = 1;
                try {
                    while (true) {
                        String msg = "Потік №" + Thread.currentThread().getName() + " згенерував повідомлення " + messageCount++;
                        buffer1.put(msg);
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            generator.setName(String.valueOf(i));
            generator.setDaemon(true);
            generator.start();
        }

        for (int i = 1; i <= 2; i++) {
            Thread translator = new Thread(() -> {
                try {
                    while (true) {
                        String originalMsg = buffer1.take();

                        String translatedMsg = "Потік №" + Thread.currentThread().getName() + " переклав повідомлення [" + originalMsg + "]";
                        buffer2.put(translatedMsg);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            translator.setName("T" + i);
            translator.setDaemon(true);
            translator.start();
        }

        try {
            for (int i = 1; i <= 100; i++) {
                String finalMessage = buffer2.take();
                System.out.println(i + ": " + finalMessage);
            }

            System.out.println("\nУспіх! 100 повідомлень успішно оброблено. Програма завершує роботу.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
