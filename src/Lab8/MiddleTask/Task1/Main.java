package Lab8.MiddleTask.Task1;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final int SIZE = 1_000_000;
        int[] array = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt(101);
        }

        System.out.println("=== Обчислення суми через ForkJoin ===");
        System.out.println("Розмір масиву: " + SIZE);

        long sequentialSum = 0;
        long startSeq = System.currentTimeMillis();
        for (int num : array) {
            sequentialSum += num;
        }
        long timeSeq = System.currentTimeMillis() - startSeq;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        ArraySumTask task = new ArraySumTask(array, 0, array.length);

        long startParallel = System.currentTimeMillis();
        long parallelSum = pool.invoke(task);
        long timeParallel = System.currentTimeMillis() - startParallel;

        System.out.println("\nРезультати ForkJoin:");
        System.out.println("Сума: " + parallelSum);
        System.out.println("Час виконання (багатопотоково): " + timeParallel + " мс");

        System.out.println("\nПеревірка (послідовне додавання):");
        System.out.println("Сума: " + sequentialSum);
        System.out.println("Час виконання (один потік): " + timeSeq + " мс");

        if (parallelSum == sequentialSum) {
            System.out.println("\nАлгоритм відпрацював коректно!");
        }
    }
}
