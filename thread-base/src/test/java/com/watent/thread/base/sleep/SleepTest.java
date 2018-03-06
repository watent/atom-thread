package com.watent.thread.base.sleep;

/**
 * sleep方法是否会释放锁？
 *
 *  sleep方法不会释放锁 B 等待 A 释放锁后才能继续执行
 */
public class SleepTest {
    //锁
    private final Object lock = new Object();

    public static void main(String[] args) {
        SleepTest sleepTest = new SleepTest();
        Thread sleepThreadA = sleepTest.new SleepThread();
        sleepThreadA.setName("ThreadSleep");
        Thread notSleepThreadB = sleepTest.new NotSleepThread();
        notSleepThreadB.setName("ThreadNotSleep");
        sleepThreadA.start();
        try {
            Thread.sleep(1000);
            System.out.println(" RunTest sleep!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notSleepThreadB.start();
    }

    private class SleepThread extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock");
            try {
                //拿到锁以后，休眠
                synchronized (lock) {
                    System.out.println(threadName + " taking the lock");
                    System.out.println("Finish the work: " + threadName);
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class NotSleepThread extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock time=" + System.currentTimeMillis());
            //拿到锁以后不休眠
            synchronized (lock) {
                System.out.println(threadName + " taking the lock time=" + System.currentTimeMillis());
                System.out.println("Finish the work: " + threadName);
            }
        }
    }
}
