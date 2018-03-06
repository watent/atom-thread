package com.watent.thread.container;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环栅栏
 * <p>
 * 每一次计数完毕 执行 barrierAction 方法
 * <p>
 * 模拟集合N士兵集合 执行任务
 */
public class CyclicBarrierTest {

    static class Soldier implements Runnable {

        private String name;

        private CyclicBarrier cyclicBarrier;

        public Soldier(CyclicBarrier cyclicBarrier, String name) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {

            try {
                //等待所有士兵到齐
                cyclicBarrier.await();
                doTask();
                //都完成任务后 一起报告
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        //执行任务
        private void doTask() {

            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " task complete");
        }
    }

    static class BarrierRun implements Runnable {

        boolean flag;

        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("commander:[Soldier total " + N + "个 complete task]");
            } else {
                System.out.println("commander:[Soldier total " + N + "个 collection complete]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {

        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("collection Team ");
        for (int i = 0; i < N; ++i) {
            System.out.println("Soldier " + i + " reported");
            allSoldier[i] = new Thread(new Soldier(cyclicBarrier, "Soldier " + i));
            allSoldier[i].start();
        }
    }

}
