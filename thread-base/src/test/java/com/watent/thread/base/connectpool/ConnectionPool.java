package com.watent.thread.base.connectpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库链接池
 * 从链接池中获取 获取和释放的过程 客户端获取链接的过程被设置程等待超时的模式
 * 即 1000毫秒内无法获取链接 返回给客户端一个null
 * 设定链接池大小为10个 通过调节客户端线程数来模拟无法获取链接的场景
 * 链接池定义：
 * 通过构造函数来初始化最大链接上限 通过一个双向队列来维护链接
 * 调用方需要先调用fetchConnection()来指定多少毫秒内获取链接
 * 连接使用完成后需要调用releaseConnection()释放 将连接放回连接池ø
 */
public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.getConnectiong());
            }
        }
    }

    //将连接放回线程池
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                //添加后需进行通知 这样其他消费者能够感知链接池已经返回了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // mills 毫秒内无法获取链接 返回给客户端一个null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            //完全超时
            if (mills <= 0) {
//                while (pool.isEmpty()) {
//                    pool.wait();
//                }
//                return pool.removeFirst();
                return null;
            } else {
                //超时时刻
                long future = System.currentTimeMillis() + mills;
                //超时时长
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    //需要等待时长
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
