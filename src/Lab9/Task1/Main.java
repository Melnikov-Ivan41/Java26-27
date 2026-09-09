package Lab9.Task1;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int NUM_ACCOUNTS = 200;
        int NUM_TRANSFERS = 10_000;

        Account[] accounts = new Account[NUM_ACCOUNTS];
        Random random = new Random();

        long initialTotalMoney = 0;
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            int initialBalance = random.nextInt(10000) + 500;
            accounts[i] = new Account(i, initialBalance);
            initialTotalMoney += initialBalance;
        }

        System.out.println("=== Банківські перекази (Task 1) ===");
        System.out.println("Сума грошей у банку ДО переказів: " + initialTotalMoney);

        Bank bank = new Bank();

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(NUM_TRANSFERS);

        for (int i = 0; i < NUM_TRANSFERS; i++) {
            executor.submit(() -> {
                Account from = accounts[random.nextInt(NUM_ACCOUNTS)];
                Account to = accounts[random.nextInt(NUM_ACCOUNTS)];
                int amount = random.nextInt(1000) + 1;

                bank.transfer(from, to, amount);

                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        long finalTotalMoney = 0;
        for (Account acc : accounts) {
            finalTotalMoney += acc.getBalance();
        }

        System.out.println("Сума грошей у банку ПІСЛЯ переказів: " + finalTotalMoney);

        if (initialTotalMoney == finalTotalMoney) {
            System.out.println("УСПІХ: Баланс зійшовся. Дедлоків не виявлено!");
        } else {
            System.out.println("ПОМИЛКА: Баланс порушено, відбулася втрата даних (Race Condition).");
        }
    }
}
