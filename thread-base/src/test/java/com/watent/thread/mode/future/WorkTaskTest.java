package com.watent.thread.mode.future;

import java.util.concurrent.*;

/**
 * CompletionService实际上可以看做是Executor和BlockingQueue的结合体。
 */
public class WorkTaskTest {

    private final int POOL_SIZE = 5;
    private final int TOTAL_TASK = 10;

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    private void testByQueue() {

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        LinkedBlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }

//        for (Future<String> f : queue) {
//            try {
//                  // 只是遍历 不拿出数据
//                System.out.println("ExecTask get " + f.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        for (int i = 0; i < TOTAL_TASK; i++) {
            try {
                //take 会出队 出队并遍历
                System.out.println("ExecTask takeGet " + queue.take().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompletion() {

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<String> service = new ExecutorCompletionService<String>(pool);


        for (int i = 0; i < TOTAL_TASK; i++) {
            service.submit(new WorkTask("ExecTask" + i));
        }
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future;
            try {
                future = service.take();
                System.out.println("CompletionService " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WorkTaskTest completionTest = new WorkTaskTest();
        completionTest.testByQueue();
//        completionTest.testByCompletion();
    }


}
