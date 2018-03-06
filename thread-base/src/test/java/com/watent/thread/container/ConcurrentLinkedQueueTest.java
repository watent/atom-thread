package com.watent.thread.container;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 无界非阻塞队列
 *
 * queue.isEmpty() queue.size()>0  性能问题
 */
public class ConcurrentLinkedQueueTest {

    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private static int count = 50000;
    private static int count2 = 2;
    private static CountDownLatch cd = new CountDownLatch(count2);

    public static void dothis() {
        for (int i = 0; i < count; i++) {
            queue.offer(i);
        }
    }

    static class Poll implements Runnable {

        @Override
        public void run() {
            // 性能低
//            while (queue.size()>0) {
            while (!queue.isEmpty()) {
                queue.poll();
            }
            cd.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueueTest.dothis();
        //启用两个线程取数据
        for (int i = 0; i < count2; i++) {
            es.submit(new Poll());
        }
        cd.await();
        System.out.println("cost time " + (System.currentTimeMillis() - timeStart) + "ms");
        es.shutdown();
    }


}
