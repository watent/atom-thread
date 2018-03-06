package com.watent.thread.pool;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 提交线程 Executors.execute()
 * 1.当前线程数小于corePoolSize 通过 addWorker() 直接调度执行 否则加入等待队列 workQueue
 * 2.加入等待队列失败 任务直接交给线程池 创建新的线程
 * 3.如已达到maximumPoolSize 则提交失败
 * 4.执行拒绝策略
 */
public class PoolTest {

    public static class MyTask implements Runnable {

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " thread Id " + Thread.currentThread().getId() + " name: " + Thread.currentThread().getName());
            SleepUtils.second(10);
        }
    }


    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        //获取CPU数量
        Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < 5; i++) {
            executor.submit(new MyTask("task" + i));
        }

    }

}
