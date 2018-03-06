package com.watent.thread.principle.aqs;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.locks.Lock;

public class MyLockTest {

    public void test() {
        final Lock lock = new Mutex();
//        final Lock lock = new TwoThreadsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                    SleepUtils.second(2);
                }
            }
        }
        // 启动10个子线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println("*");
        }
    }

    public static void main(String[] args) {
        MyLockTest testMyLock = new MyLockTest();
        testMyLock.test();
    }
}
