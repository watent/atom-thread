package com.watent.thread.base.template;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class RwLockTemplete {

    static final Map<String, String> map = new HashMap<>();
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    static Lock r = reentrantReadWriteLock.readLock();
    static Lock w = reentrantReadWriteLock.writeLock();

    public void put() {
        //在try里面 异常时容易造成锁的释放
        w.lock();
        try {
            // do my work.....
        } finally {
            w.unlock();
        }
    }

    public void get() {
        r.lock();
        try {
            // do my work.....
        } finally {
            r.unlock();
        }
    }

}
