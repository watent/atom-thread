package com.watent.thread.base.template;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件锁
 */
public class ConditionTemplete {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void waitc() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void waitnotify() throws InterruptedException {
        lock.lock();
        try {
            condition.signal();
            //condition.signalAll();尽量少使用
        } finally {
            lock.unlock();
        }
    }


}
