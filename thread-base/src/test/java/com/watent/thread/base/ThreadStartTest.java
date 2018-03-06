package com.watent.thread.base;

public class ThreadStartTest {

    private static class TestThread extends Thread {
        @Override
        public void run() {
            System.out.println("TestThread is runing");
        }
    }

    private static class TestRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("TestRunnable is runing");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new TestThread();
        Thread t2 = new Thread(new TestRunnable());
        t1.start();
        t2.start();

    }
}
