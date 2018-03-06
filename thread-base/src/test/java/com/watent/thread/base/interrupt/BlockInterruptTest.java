package com.watent.thread.base.interrupt;

/**
 * 阻塞中断
 * 调用阻塞方法时，如何中断线程
 */
public class BlockInterruptTest {

    private static volatile boolean on = true;

    private static class WhenBlock implements Runnable {

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                try {
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位改成false
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//重新设置一下
                    //do my work
                }
                //清理工作结束线程
            }
        }

        public void cancel() {
            on = false;
            Thread.currentThread().interrupt();
        }

    }
}
