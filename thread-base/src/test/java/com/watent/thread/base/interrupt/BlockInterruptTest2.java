package com.watent.thread.base.interrupt;

/**
 * 阻塞中断
 * while try 注意点
 * 调用阻塞方法时，如何中断线程
 */
public class BlockInterruptTest2 {

    private static final Object o = new Object();

    private static volatile boolean on = true;

    /*while循环中包含try/catch块*/
    private static class WhileTryWhenBlock extends Thread {

        private volatile boolean on = true;
        private long i = 0;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                try {
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位会改成false
                    //可以理解为这些方法会隐含调用Thread.interrupted()方法
                    synchronized (o) {
                        o.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("当前执行线程的中断标志位：" + Thread.currentThread().getId() + ":" + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();//重新设置一下
                    System.out.println("中断后：" + Thread.currentThread().getId() + ":" + Thread.currentThread().isInterrupted());
                    System.out.println("被中断的线程_" + getId() + ":" + isInterrupted());
                    //do my work
                }
                //清理工作结束线程
            }
        }

        public void cancel() {
            //on = false;
            interrupt();
            System.out.println("本方法所在线程实例：" + getId());
            System.out.println("执行本方法的线程：" + Thread.currentThread().getId());
            //Thread.currentThread().interrupt(); //注意 中断调用线程
        }
    }

    /*try/catch块中包含while循环*/
    private static class TryWhileWhenBlock extends Thread {
        private volatile boolean on = true;
        private long i = 0;

        @Override
        public void run() {
            try {
                while (on) {
                    System.out.println(i++);
                    //抛出中断异常的阻塞方法，抛出异常后，中断标志位改成false
                    synchronized (o) {
                        o.wait();
                    }
                }
            } catch (InterruptedException e) {
                //抛出异常 线程就执行完毕
                System.out.println("当前执行线程的中断标志位："
                        + Thread.currentThread().getId()
                        + ":" + this.isInterrupted());
            } finally {
                //清理工作结束线程
            }
        }

        public void cancel() {
            on = false;
            interrupt();
            System.out.println("本方法所在线程实例：" + getId());
            System.out.println("执行本方法的线程：" + Thread.currentThread().getId());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        WhileTryWhenBlock whileTryWhenBlock = new WhileTryWhenBlock();
        whileTryWhenBlock.start();
        Thread.sleep(100);
        whileTryWhenBlock.cancel();//主线程调用 - 类实例调用 cancel () Jvm会把当前方法加载到当前线程栈帧顶部执行

//        TryWhileWhenBlock tryWhileWhenBlock = new TryWhileWhenBlock();
//        tryWhileWhenBlock.start();
//        Thread.sleep(100);
//        tryWhileWhenBlock.cancel();
    }
}
