package com.watent.thread.base.interrupt;

/**
 * 标记取消
 * 使用自定义的取消标志位中断线程（不安全）
 */
public class FlagCancelTest {

    private static class TestRunnable implements Runnable {

        private volatile boolean on = true;
        private long i = 0;

        @Override
        public void run() {
            while (on) {
                i++;
                //阻塞方法，on不起作用
                //wait,sleep,blocking queue(put,take)
                try {
                    wait();
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("TestRunnable is runing :" + i);
        }

        public void cancel() {
            on = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestRunnable testRunnable = new TestRunnable();
        Thread thread = new Thread(testRunnable);
        thread.start();
        Thread.sleep(10);
        testRunnable.cancel();
    }

}