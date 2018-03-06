package com.watent.thread.container.delayqueue;

import java.util.concurrent.DelayQueue;

public class GetFromCache implements Runnable {

    private DelayQueue<CacheBean<User>> queue;

    public GetFromCache(DelayQueue<CacheBean<User>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (true) {
            try {
                CacheBean<User> item = queue.take();
                System.out.println("GetFromCache" + item.getId() + " : " + item.getName() + " data: " + (item.getData()).getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
