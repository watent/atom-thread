package com.watent.thread.container.delayqueue;

import java.util.concurrent.DelayQueue;

public class PutInCache implements Runnable {

    private DelayQueue<CacheBean<User>> queue;

    public PutInCache(DelayQueue<CacheBean<User>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        CacheBean<User> bean1 = new CacheBean<>("1", "5s", new User("u1"), 5000L);
        CacheBean<User> bean2 = new CacheBean<>("2", "2s", new User("u2"), 2000L);

        queue.offer(bean1);
        System.out.println("put in cache:" + bean1.getId() + ":" + bean1.getName());
        queue.offer(bean2);
        System.out.println("put in cache:" + bean2.getId() + ":" + bean2.getName());

    }
}
