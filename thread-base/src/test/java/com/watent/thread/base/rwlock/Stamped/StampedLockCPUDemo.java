package com.watent.thread.base.rwlock.Stamped;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock (CLH锁) 注意点 since Java8
 * park()函数在线程中断时会直接返回 参考 LockSupportDemo
 * <p>
 * StampedLock readLock&writeLock 没有处理中断的逻辑 在循环中会一直请求 导致中断后再次进入循环 退出条件不满足会导致疯狂占用CPU
 */
public class StampedLockCPUDemo {

    private static Thread[] holdCpuThreads = new Thread[3];

    static final StampedLock lock = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            long readLong = lock.writeLock();
            LockSupport.parkNanos(600000000000L);
            lock.unlockWrite(readLong);
        }).start();

        Thread.sleep(100);

        for (int i = 0; i < 3; i++) {
            holdCpuThreads[i] = new Thread(new HoldCPUReadThread());
            holdCpuThreads[i].start();
        }
        Thread.sleep(10000);
        for (int i = 0; i < 3; i++) {
            holdCpuThreads[i].interrupt();
        }
    }

    private static class HoldCPUReadThread implements Runnable {

        @Override
        public void run() {
            long lockr = lock.readLock();
            System.out.println(Thread.currentThread().getName() + "获得读锁");
            lock.unlockRead(lockr);
        }
    }

}
