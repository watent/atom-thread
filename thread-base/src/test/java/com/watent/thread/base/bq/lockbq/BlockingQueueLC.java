package com.watent.thread.base.bq.lockbq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock Condition 有限阻塞队列
 */
public class BlockingQueueLC<T> {

    private List<T> queue = new LinkedList<>();
    private final int limit;
    private Lock lock = new ReentrantLock();
    private Condition needNotEmpty = lock.newCondition();
    private Condition needNotFull = lock.newCondition();


    public BlockingQueueLC(int limit) {
        this.limit = limit;
    }

    //入队
    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        try {
            while (this.queue.size() == this.limit) {
                needNotFull.await();
            }
            this.queue.add(item);
            needNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    //出队
    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (this.queue.size() == 0) {
                needNotEmpty.await();
            }
            needNotFull.signal();
            return this.queue.remove(0);
        } finally {
            lock.unlock();
        }
    }
}
