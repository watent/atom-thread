package com.watent.thread.base.interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 覆盖线程的中断方法
 */
public class OverrideInterruptTest extends Thread {

    private final Socket socket;
    private final InputStream in;

    public OverrideInterruptTest(Socket socket, InputStream in) {
        this.socket = socket;
        this.in = in;
    }

    private void t() {
    }

    //覆盖线程的 Thread.interrupt 方法
    @Override
    public void interrupt() {
        try {
            //关闭底层的套接字
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            //.....
        } finally {
            //同时中断线程
            super.interrupt();
        }

    }
}