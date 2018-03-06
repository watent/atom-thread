package com.watent.thread.principle.future.newFuture;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture
 * <p>
 * 完成通知 异步调用 流式调用 异常处理 组合
 */
public class AskThread implements Runnable {

    CompletableFuture<Integer> re = null;

    public AskThread(CompletableFuture<Integer> re) {
        this.re = re;
    }

    @Override
    public void run() {

        int myRe = 0;
        try {
            myRe = re.get() * re.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(myRe);
    }

    public static Integer calc(Integer para) {

        SleepUtils.second(1);
        return para * para;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)).start();

        SleepUtils.second(1);
        //如没有完成 用传递的值返回
        future.complete(60);

        //异步调用
        CompletableFuture<Integer> futureAsync = CompletableFuture.supplyAsync(() -> calc(10))
                .exceptionally(ex -> {
                    System.out.println(ex);
                    return -1;
                });
        System.out.println(futureAsync.get());

    }
}
