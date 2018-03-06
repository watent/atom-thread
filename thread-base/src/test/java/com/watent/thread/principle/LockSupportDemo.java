package com.watent.thread.principle;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 * park unpark 阻塞和释放线程
 * <p>
 * park 响应中断
 */
public class LockSupportDemo {

    public static Object lock = new Object();

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                //中断后不会抛出 InterruptedException 默默返回
                LockSupport.park();
                //park 响应中断
                if (Thread.interrupted()) {
                    System.out.println("int " + getName() + " interrupted");
                }
                System.out.println("out " + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ChangeObjectThread t1 = new ChangeObjectThread("A");
        ChangeObjectThread t2 = new ChangeObjectThread("B");

        t1.start();
        Thread.sleep(1000);
        t2.start();

//        Thread.sleep(5000);
//        LockSupport.unpark(t1);
//        LockSupport.unpark(t2);
//        t1.join();
//        t2.join();
        // 中断 LockSupport.park();
        t1.interrupt();
        LockSupport.unpark(t2);

    }

}
