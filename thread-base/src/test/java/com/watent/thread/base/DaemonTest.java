package com.watent.thread.base;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 */
public class DaemonTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner());
        //守护线程
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
