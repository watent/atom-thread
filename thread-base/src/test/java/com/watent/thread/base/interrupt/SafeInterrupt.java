package com.watent.thread.base.interrupt;

/**
 * 安全中断
 * 标志位&中断信号
 */
public class SafeInterrupt implements Runnable {

    private volatile boolean on = true;
    private long i = 0;

    @Override
    public void run() {
        while (on && Thread.currentThread().isInterrupted()) {
            i++;
        }
        System.out.println("TestRunnable is runing :" + i);
    }

    public void cancel() {
        on = false;
        Thread.currentThread().interrupt();
    }
}