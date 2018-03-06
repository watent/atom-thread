package com.watent.thread.container;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 倒计数器
 * 模拟火箭发射
 */
public class CountDownLatchTest implements Runnable {

    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    private static CountDownLatchTest countDownLatchTest = new CountDownLatchTest();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println("check complete");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(countDownLatchTest);
        }

        countDownLatch.await();
        System.out.println("Fire");
        executorService.shutdown();
    }
}
