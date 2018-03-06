package com.watent.thread.mode.forkjoin.pig;

import com.watent.thread.mode.forkjoin.monkey.MonkeyPickImpl;
import com.watent.thread.mode.forkjoin.monkey.MonkeyProcessImpl;
import com.watent.thread.mode.forkjoin.service.IPickPeach;
import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.MakePanTaoArray;
import com.watent.thread.mode.forkjoin.vo.Peach;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class PigAsync {

    private static class SmallPig extends RecursiveAction {

        private final static int THRESHOLD = 100;
        private Peach[] src;
        private int fromIndex;
        private int toIndex;
        private IPickPeach pickTaoZi;

        public SmallPig(Peach[] src, int fromIndex, int toIndex, IPickPeach pickTaoZi) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.pickTaoZi = pickTaoZi;
        }

        @Override
        protected void compute() {
            if (toIndex - fromIndex < THRESHOLD) {
                System.out.println(" from index = " + fromIndex + " toIndex=" + toIndex);
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    if (pickTaoZi.pick(src, i)) count++;
                }
            } else {
                //fromIndex....mid.....toIndex
                int mid = (fromIndex + toIndex) / 2;
                SmallPig left = new SmallPig(src, fromIndex, mid, pickTaoZi);
                SmallPig right = new SmallPig(src, mid + 1, toIndex, pickTaoZi);
                invokeAll(left, right);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool();
        Peach[] src = MakePanTaoArray.makeArray();
        IProcessPeach processTaoZi = new MonkeyProcessImpl();
        IPickPeach pickTaoZi = new MonkeyPickImpl(processTaoZi);

        long start = System.currentTimeMillis();

        SmallPig smallPig = new SmallPig(src, 0,
                src.length - 1, pickTaoZi);

        pool.execute(smallPig);
        System.out.println("BaJie is picking.....");

        Thread.sleep(2);
        System.out.println("Please waiting.....");

        while (!smallPig.isDone()) {
            showLog(pool);
            TimeUnit.MILLISECONDS.sleep(100);
        }
//
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        showLog(pool);

        smallPig.join();
        System.out.println(" spend time:" + (System.currentTimeMillis() - start) + "ms");
    }

    //监控Fork/Join池相关方法
    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");

        System.out.printf("线程池的worker线程们的数量:%d\n",
                pool.getPoolSize());
        System.out.printf("当前执行任务的线程的数量:%d\n",
                pool.getActiveThreadCount());
        System.out.printf("没有被阻塞的正在工作的线程:%d\n",
                pool.getRunningThreadCount());
        System.out.printf("已经提交给池还没有开始执行的任务数:%d\n",
                pool.getQueuedSubmissionCount());
        System.out.printf("已经提交给池已经开始执行的任务数:%d\n",
                pool.getQueuedTaskCount());
        System.out.printf("线程偷取任务数:%d\n",
                pool.getStealCount());
        System.out.printf("池是否已经终止 :%s\n",
                pool.isTerminated());
        System.out.printf("**********************\n");
    }
}
