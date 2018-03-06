package com.watent.thread.mode.future;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.Callable;

public class ComputeTask implements Callable<Integer> {

    private Integer result;

    private String taskName;

    public ComputeTask(Integer result, String taskName) {
        this.result = result;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws Exception {

        for (int i = 0; i < 100; i++) {
            result += i;
        }
        SleepUtils.second(1);
        System.out.println("子任务完成");
        return result;
    }
}
