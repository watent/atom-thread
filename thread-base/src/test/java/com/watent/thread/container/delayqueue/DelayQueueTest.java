package com.watent.thread.container.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {

        DelayQueue<CacheBean<User>> queue = new DelayQueue<>();

        Thread putThread = new Thread(new PutInCache(queue));
        Thread getThread = new Thread(new GetFromCache(queue));
        putThread.start();
        getThread.start();

        for (int i = 1; i < 20; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }

    }

}
