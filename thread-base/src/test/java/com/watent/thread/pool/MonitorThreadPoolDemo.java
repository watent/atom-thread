package com.watent.thread.pool;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.*;

/**
 * 监控线程执行
 * 覆写 beforeExecute afterExecute
 */
public class MonitorThreadPoolDemo {


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

        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:" + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                try {
                    t.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("完成执行 ok :" + r.toString());
            }

            @Override
            protected void terminated() {
                System.out.println("pool exit");
            }

        };

        for (int i = 0; i < 1000; i++) {
            MyTask task = new MyTask("Task" + i);
            executorService.submit(task);
            SleepUtils.second(1);
        }
//        for (int i = 0; i < 5; i++) {
//            executorService.execute(new TraceThreadPoolExecutor.DivTask(100, i));
//        }
        SleepUtils.second(5);
        executorService.shutdown();
    }

}
