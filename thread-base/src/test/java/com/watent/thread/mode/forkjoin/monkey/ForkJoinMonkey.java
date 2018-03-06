package com.watent.thread.mode.forkjoin.monkey;

import com.watent.thread.mode.forkjoin.service.IPickPeach;
import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.MakePanTaoArray;
import com.watent.thread.mode.forkjoin.vo.Peach;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMonkey {

    private static class SmallMonkey extends RecursiveTask<Integer> {

        private final static int THRESHOLD = 100;//阈值，数组多小，进行具体的业务操作

        private Peach[] src;
        private int fromIndex;
        private int toIndex;
        private IPickPeach iPickPeach;

        public SmallMonkey(Peach[] src, int fromIndex, int toIndex, IPickPeach iPickPeach) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.iPickPeach = iPickPeach;
        }

        @Override
        protected Integer compute() {

            if (toIndex - fromIndex < THRESHOLD) {
                int count = 0;
                for (int i = fromIndex; i < toIndex; i++) {
                    if (iPickPeach.pick(src, i))
                        count++;
                }
                return count;
            } else {
                int mid = (fromIndex + toIndex) / 2;
                SmallMonkey left = new SmallMonkey(src, fromIndex, mid, iPickPeach);
                SmallMonkey right = new SmallMonkey(src, mid, toIndex, iPickPeach);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();

        Peach[] src = MakePanTaoArray.makeArray();
        IProcessPeach processPeach = new MonkeyProcessImpl();
        IPickPeach pickPeach = new MonkeyPickImpl(processPeach);

        long start = System.currentTimeMillis();
        SmallMonkey smallMonkey = new SmallMonkey(src, 0, src.length - 1, pickPeach);

        pool.invoke(smallMonkey);
        //System.out.println("Task is Running.....");
        System.out.println("The count is " + smallMonkey.join() + " spend time:" + (System.currentTimeMillis() - start) + "ms");

    }

}
