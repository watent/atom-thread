package com.watent.thread.base.connectpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);
    // 保证所有ConnectionRunner 同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main 线程将会等待所有 ConnectionRunner 结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        // 线程数量 可以对线程数量观察
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        //每个线程循环的次数
        int count = 20;
        AtomicInteger got = new AtomicInteger();//获取数据库连接的次数
        AtomicInteger notGot = new AtomicInteger();//没有获取到数据库连接的次数
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection:  " + got);
        System.out.println("not got connection " + notGot);
    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got,
                                AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            try {
                start.await();
            } catch (Exception ex) {

            }
            while (count > 0) {
                try {
                    //从线程池中获取链接
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
