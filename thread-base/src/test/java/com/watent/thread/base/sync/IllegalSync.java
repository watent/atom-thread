package com.watent.thread.base.sync;

/**
 * 非法同步
 * <p>
 * synchronized 要 锁final 实例
 */
public class IllegalSync implements Runnable {

    public Integer count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (count) {
                // 等价于count = Integer.valueOf(i.intValue() + 1);
                // Integer.valueOf  会创建一个新的实例
                // 锁的引用改变了 多线程下不是一把锁
                count++;
            }
        }
    }

    public Integer getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        IllegalSync instance = new IllegalSync();

        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        //将会得到一个小于2w的数字
        System.out.println(instance.getCount());
    }
}
