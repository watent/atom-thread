package com.watent.thread.mode.future;

import com.watent.thread.base.util.SleepUtils;

import java.util.Random;
import java.util.concurrent.Callable;

public class WorkTask implements Callable<String> {

    private String name;

    public WorkTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {

        Random random = new Random();
        Integer count = random.nextInt(10);
        SleepUtils.second(count);

        return "sleep count" + count;
    }
}
