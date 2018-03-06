package com.watent.thread.mode.parallelsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelSearchDemo {

    private static int arr[];

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private static final int THREAD_NUM = 2;

    private static AtomicInteger result = new AtomicInteger();

    public static int search(int searchValue, int beginPos, int endPos) {

        int i;
        for (i = beginPos; i < endPos; i++) {

            if (result.get() > 0) {
                return result.get();
            }
            if (arr[i] == searchValue) {
                //如设置失败 则表示其他线程先找到了
                if (!result.compareAndSet(-1, i)) {
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer> {

        int begin, end, searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() throws Exception {
            return null;
        }
    }

    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {

        int subArrSize = arr.length / THREAD_NUM + 1;
        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {

            int end = i + subArrSize;
            if (end >= arr.length) {
                end = arr.length;
            }
            list.add(pool.submit(new SearchTask(searchValue, i, end)));
        }

        for (Future<Integer> f : list) {
            if (f.get() > 0) {
                return f.get();
            }
        }
        return -1;
    }

}
