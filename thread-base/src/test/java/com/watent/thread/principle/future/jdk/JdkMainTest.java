package com.watent.thread.principle.future.jdk;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class JdkMainTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask<>(new RealData("a"));

        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 发送请求 开启线程 执行 call
        executor.submit(futureTask);

        System.out.println("请求完毕");
        SleepUtils.second(2);
        //取得 call 方法返回值
        System.out.println("数据:" + futureTask.get());

    }

}
