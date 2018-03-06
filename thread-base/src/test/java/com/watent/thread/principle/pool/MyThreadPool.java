package com.watent.thread.principle.pool;


import java.util.LinkedList;
import java.util.List;

/**
 * 线程池
 */
public class MyThreadPool {

    //默认线程个数
    private int workNum = 5;
    //线程容器
    private WorkThread[] workThreads;
    //任务队列
    private List<Runnable> taskQueue = new LinkedList<>();

    public MyThreadPool(int workNum) {

        this.workNum = workNum;

        workThreads = new WorkThread[workNum];
        for (int i = 0; i < workNum; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        synchronized (taskQueue) {
            taskQueue.add(runnable);
            taskQueue.notify();
        }
    }

    public void destroy() {
        System.out.println("ready stop pool....");
        for (int i = 0; i < workNum; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }
        taskQueue.clear();
    }

    //工作线程
    private class WorkThread extends Thread {

        private volatile boolean on = true;

        @Override
        public void run() {

            Runnable r = null;
            try {
                while (on && !isInterrupted()) {
                    synchronized (taskQueue) {
                        while (on && isInterrupted() && taskQueue.isEmpty()) {
                            //工作线程中无任务
                            taskQueue.wait(1000);
                        }
                        //任务队列中有任务，拿任务做事
                        if (on && !isInterrupted() && !taskQueue.isEmpty()) {
                            r = taskQueue.remove(0);
                        }
                        if (r != null) {
                            System.out.println(getId() + " ready execute....");
                            r.run();
                        }
                        //加速垃圾回收
                        r = null;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId() + " is Interrupted");
            }
        }

        public void stopWorker() {
            on = false;
            interrupt();
        }
    }

}
