package com.watent.thread.mode.future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureSample
 */
public class ComputeTaskTest {

    public static void main(String[] args) {

        List<FutureTask<Integer>> taskList = new LinkedList<>();

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {

            FutureTask<Integer> futureTask = new FutureTask<>(new ComputeTask(i, "task" + i));
            taskList.add(futureTask);
            service.submit(futureTask);
        }

        System.out.println("已经提交任务");

        // 开始统计各计算线程计算结果
        int totalResult = 0;
        for (FutureTask<Integer> futureTask : taskList) {
            try {
                totalResult += futureTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("total:" + totalResult);

        service.shutdown();

    }


}
