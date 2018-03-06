package com.watent.thread.container;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 用信号量实现有界缓存
 *
 * @param <T>
 */
public class SemaphporeCase<T> {

    private final Semaphore items;//有多少元素可拿
    private final Semaphore space;//有多少空位可放元素
    private List<T> queue = new LinkedList<>();

    public SemaphporeCase(int itemCounts) {
        this.items = new Semaphore(1);
        this.space = new Semaphore(itemCounts);
    }

    //入队
    public void put(T t) throws InterruptedException {

        //拿空位的许可，没有空位线程会在这个方法上阻塞
        space.acquire();
        synchronized (SemaphporeCase.class) {
            queue.add(t);
        }
        //有元素了，可以释放一个拿元素的许可
        items.release();

    }

    //出队
    public void poll() throws InterruptedException {

        //拿元素的许可，没有元素线程会在这个方法上阻塞
        items.acquire();
        synchronized (SemaphporeCase.class) {
            queue.remove(0);
        }
        //有空位了，可以释放一个存在空位的许可
        space.release();
    }

}
