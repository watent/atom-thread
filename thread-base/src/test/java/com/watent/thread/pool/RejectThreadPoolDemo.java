package com.watent.thread.pool;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.*;

/**
 * 自定义拒绝策略
 */
public class RejectThreadPoolDemo {


    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " thread Id " + Thread.currentThread().getId() + " name: " + Thread.currentThread().getName());
            SleepUtils.second(10);
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                (r, executor) -> System.out.println(r.toString() + " is discard"));//自定义拒绝策略

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            executorService.submit(task);
            SleepUtils.second(1);
        }
    }

}
