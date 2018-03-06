package com.watent.thread.base.bq;

import java.util.LinkedList;
import java.util.List;

/**
 * 等待通知机制实现有界缓存
 *
 * @param <T>
 */
public class BlockingQueue<T> {

    private List<T> queue = new LinkedList<>();

    private final int limit;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    //入队
    public synchronized void enqueue(T t) throws InterruptedException {

        while (limit == queue.size()) {
            this.wait();
        }
        //将数据入队，可以肯定有出队的线程正在等待
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.add(t);
    }

    //出队
    public synchronized T dequeue() throws InterruptedException {

        while (queue.size() == 0) {
            this.wait();
        }
        if (limit == queue.size()) {
            notifyAll();
        }
        return queue.remove(0);
    }


}
