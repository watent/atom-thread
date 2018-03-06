package com.watent.thread.base.template;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁
 */
public class LockTemplate {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            // do my work.....
        }finally{
            lock.unlock();
        }
    }

}
